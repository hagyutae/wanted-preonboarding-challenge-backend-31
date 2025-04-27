import { Test, TestingModule } from "@nestjs/testing";

import { Product_Image, Product_Option } from "src/domain";
import { ProductOptionsService } from "src/application";
import { ImageBodyDTO, OptionBodyDTO, OptionParamDTO } from "../dto";
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
      const param = { id: 1, option_id: 2 };
      const body = { option_group_id: 2, name: "Option 1" } as OptionBodyDTO;
      const data = { id: param.id, ...body } as Product_Option;
      jest.spyOn(mockService, "addOptions").mockResolvedValue(data);

      const result = await mockController.addOptions(param, body);

      expect(mockService.addOptions).toHaveBeenCalledWith(param, body);
      expect(result).toEqual({
        success: true,
        data: { id: 1, name: "Option 1" },
        message: "상품 옵션이 성공적으로 추가되었습니다.",
      });
    });
  });

  describe("updateOptions", () => {
    it("상품 옵션 수정 성공", async () => {
      const param = { id: 1, option_id: 2 };
      const body = { name: "Updated Option" } as OptionBodyDTO;
      const data = {
        id: param.id,
        option_group_id: param.option_id,
        ...body,
      } as Product_Option;
      jest.spyOn(mockService, "updateOptions").mockResolvedValue(data);

      const result = await mockController.updateOptions(param, body);

      expect(mockService.updateOptions).toHaveBeenCalledWith(param.id, param.option_id, body);
      expect(result).toEqual({
        success: true,
        data: {
          id: param.id,
          option_group_id: param.option_id,
          ...body,
        },
        message: "상품 옵션이 성공적으로 수정되었습니다.",
      });
    });
  });

  describe("deleteOptions", () => {
    it("상품 옵션 삭제 성공", async () => {
      const param = { id: 1, option_id: 2 };
      jest.spyOn(mockService, "deleteOptions").mockResolvedValue(undefined);

      const result = await mockController.deleteOptions(param);

      expect(mockService.deleteOptions).toHaveBeenCalledWith(param.id, param.option_id);
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
      const body = { url: "http://example.com/image.jpg" } as ImageBodyDTO;
      const data = { id: param.id, url: body.url } as Product_Image;
      jest.spyOn(mockService, "addImages").mockResolvedValue(data);

      const result = await mockController.addImages(param, body);

      expect(mockService.addImages).toHaveBeenCalledWith(param.id, body);
      expect(result).toEqual({
        success: true,
        data: { id: 1, url: "http://example.com/image.jpg" },
        message: "상품 이미지가 성공적으로 추가되었습니다.",
      });
    });
  });
});
