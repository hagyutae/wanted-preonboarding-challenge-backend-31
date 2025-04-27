import { Test, TestingModule } from "@nestjs/testing";

import ProductService from "src/application/ProductService";
import ProductOptionsController from "./Product_Options.controller";
import { OptionParamDTO, PostBodyDTO } from "../dto";

describe("ProductOptionsController", () => {
  let controller: ProductOptionsController;
  let mockService: jest.Mocked<ProductService>;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [ProductOptionsController],
      providers: [
        {
          provide: ProductService,
          useValue: {
            addOptions: jest.fn(),
            updateOptions: jest.fn(),
            deleteOptions: jest.fn(),
            addImages: jest.fn(),
          },
        },
      ],
    }).compile();

    controller = module.get<ProductOptionsController>(ProductOptionsController);
    mockService = module.get(ProductService);
  });

  it("addOptions 메서드는 옵션을 추가하고 성공 메시지를 반환", async () => {
    const id = "1";
    const body = { name: "Option 1" } as PostBodyDTO;
    const addedOption = { id: "option1", ...body };
    mockService.addOptions.mockResolvedValue(addedOption);

    const result = await controller.addOptions({ id } as OptionParamDTO, body);

    expect(mockService.addOptions).toHaveBeenCalledWith(id, body);
    expect(result).toEqual({
      success: true,
      data: addedOption,
      message: "상품 옵션이 성공적으로 추가되었습니다.",
    });
  });

  it("updateOptions 메서드는 옵션을 수정하고 성공 메시지를 반환", async () => {
    const id = "1";
    const optionId = "option1";
    const body = { name: "Updated Option" } as PostBodyDTO;
    const updatedOption = { id: optionId, ...body };
    mockService.updateOptions.mockResolvedValue(updatedOption);

    const result = await controller.updateOptions({ id, optionId } as OptionParamDTO, body);

    expect(mockService.updateOptions).toHaveBeenCalledWith(id, optionId, body);
    expect(result).toEqual({
      success: true,
      data: updatedOption,
      message: "상품 옵션이 성공적으로 수정되었습니다.",
    });
  });

  it("deleteOptions 메서드는 옵션을 삭제하고 성공 메시지를 반환", async () => {
    const id = "1";
    const optionId = "option1";
    mockService.deleteOptions.mockResolvedValue(undefined);

    const result = await controller.deleteOptions({ id, optionId } as OptionParamDTO);

    expect(mockService.deleteOptions).toHaveBeenCalledWith(id, optionId);
    expect(result).toEqual({
      success: true,
      data: undefined,
      message: "상품 옵션이 성공적으로 삭제되었습니다.",
    });
  });

  it("addImages 메서드는 이미지를 추가하고 성공 메시지를 반환", async () => {
    const id = "1";
    const body = {
      images: [{ url: "http://example.com/image.jpg" }],
    } as PostBodyDTO;
    const addedImage = { id: "image1", ...body };
    mockService.addImages.mockResolvedValue(addedImage);

    const result = await controller.addImages({ id } as OptionParamDTO, body);

    expect(mockService.addImages).toHaveBeenCalledWith(id, body);
    expect(result).toEqual({
      success: true,
      data: addedImage,
      message: "상품 이미지가 성공적으로 추가되었습니다.",
    });
  });
});
