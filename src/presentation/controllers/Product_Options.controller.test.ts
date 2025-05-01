import { Test, TestingModule } from "@nestjs/testing";

import { Product_Image, Product_Option } from "src/domain/entities";
import { ProductOptionsService } from "src/application/services";
import { ImageBodyDTO, OptionBodyDTO, OptionParamDTO, ResponseDTO } from "../dto";
import ProductOptionsController from "./Product_Options.controller";

describe("ProductOptionsController", () => {
  let mockController: ProductOptionsController;
  let mockService: jest.Mocked<ProductOptionsService>;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [ProductOptionsController],
      providers: [
        {
          provide: ProductOptionsService,
          useValue: {
            addOptions: jest.fn(),
            updateOptions: jest.fn(),
            deleteOptions: jest.fn(),
            addImages: jest.fn(),
          },
        },
      ],
    }).compile();

    mockController = module.get<ProductOptionsController>(ProductOptionsController);
    mockService = module.get(ProductOptionsService);
  });

  describe("addOptions", () => {
    it("상품 옵션 추가 성공", async () => {
      const param = { id: 1 };
      const body = { name: "Option 1" } as OptionBodyDTO;
      const data = {
        id: param.id,
        ...body,
      } as Product_Option;
      mockService.register = jest.fn().mockResolvedValue(data);

      const result: ResponseDTO<any> = await mockController.create_option(param, body);

      expect(mockService.register).toHaveBeenCalledWith(param.id, body.option_group_id, body);
      expect(result).toEqual({
        success: true,
        data: { id: 1, name: "Option 1" },
        message: "상품 옵션이 성공적으로 추가되었습니다.",
      });
    });
  });

  describe("updateOptions", () => {
    it("상품 옵션 수정 성공", async () => {
      const param = { id: 1, optionId: 2 };
      const body = { name: "Updated Option" } as OptionBodyDTO;
      const data = {
        id: param.id,
        option_group_id: param.optionId,
        ...body,
      } as Product_Option;
      mockService.update = jest.fn().mockResolvedValue(data);

      const result: ResponseDTO<any> = await mockController.update_option(param, body);

      expect(mockService.update).toHaveBeenCalledWith(param.id, param.optionId, body);
      expect(result).toEqual({
        success: true,
        data: {
          id: param.id,
          option_group_id: param.optionId,
          ...body,
        },
        message: "상품 옵션이 성공적으로 수정되었습니다.",
      });
    });
  });

  describe("deleteOptions", () => {
    it("상품 옵션 삭제 성공", async () => {
      const param = { id: 1, optionId: 2 };
      mockService.remove = jest.fn().mockResolvedValue(undefined);

      const result: ResponseDTO<any> = await mockController.delete_option(param);

      expect(mockService.remove).toHaveBeenCalledWith(param.id, param.optionId);
      expect(result).toEqual({
        success: true,
        data: null,
        message: "상품 옵션이 성공적으로 삭제되었습니다.",
      });
    });
  });

  describe("addImages", () => {
    it("상품 이미지 추가 성공", async () => {
      const param = { id: 1 } as OptionParamDTO;
      const body = { option_id: 2, url: "http://example.com/image.jpg" } as ImageBodyDTO;
      const data = { id: param.id, url: body.url } as Product_Image;
      mockService.register_images = jest.fn().mockResolvedValue(data);

      const result: ResponseDTO<any> = await mockController.create_image(param, body);

      expect(mockService.register_images).toHaveBeenCalledWith(param.id, body.option_id, body);
      expect(result).toEqual({
        success: true,
        data: { id: 1, url: "http://example.com/image.jpg" },
        message: "상품 이미지가 성공적으로 추가되었습니다.",
      });
    });
  });
});
