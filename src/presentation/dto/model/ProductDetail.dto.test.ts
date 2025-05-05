import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";
import ProductDetailDTO, { DimensionsDTO, AdditionalInfoDTO } from "./ProductDetail.dto";

describe("ProductDetailDTO", () => {
  const validateDTO = async (dto: Partial<ProductDetailDTO>) => {
    const instance = plainToInstance(ProductDetailDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.constraints);
  };

  const validData: Partial<ProductDetailDTO> = {
    weight: 25.5,
    dimensions: {
      width: 200,
      height: 85,
      depth: 90,
    },
    materials: "가죽, 목재, 폼",
    country_of_origin: "대한민국",
    warranty_info: "2년 품질 보증",
    care_instructions: "마른 천으로 표면을 닦아주세요",
    additional_info: {
      assembly_required: true,
      assembly_time: "30분",
    },
  };

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = { ...validData };
    delete invalidData.materials;
    delete invalidData.country_of_origin;

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(2);
  });

  describe("DimensionsDTO", () => {
    const validateDTO = async (dto: Partial<DimensionsDTO>) => {
      const instance = plainToInstance(DimensionsDTO, dto);

      const errors = await validate(instance);

      return errors.map((error) => error.constraints);
    };

    it("유효한 DimensionsDTO 데이터로 유효성 검증을 통과", async () => {
      const validDimensions: Partial<DimensionsDTO> = {
        width: 200,
        height: 85,
        depth: 90,
      };

      const errors = await validateDTO(validDimensions);

      expect(errors).toHaveLength(0);
    });

    it("필수 필드가 누락된 경우 검증 실패", async () => {
      const invalidDimensions = {
        height: 85,
        depth: 90,
      };

      const errors = await validateDTO(invalidDimensions);

      expect(errors).not.toHaveLength(0);
    });

    it("depth가 음수인 경우 검증 실패", async () => {
      const invalidDimensions = {
        width: 200,
        height: 85,
        depth: -10,
      };

      const errors = await validateDTO(invalidDimensions);

      expect(errors).not.toHaveLength(0);
    });
  });

  describe("AdditionalInfoDTO", () => {
    const validateDTO = async (dto: Partial<AdditionalInfoDTO>) => {
      const instance = plainToInstance(AdditionalInfoDTO, dto);

      const errors = await validate(instance);

      return errors.map((error) => error.constraints);
    };

    it("유효한 AdditionalInfoDTO 데이터로 유효성 검증을 통과", async () => {
      const validAdditionalInfo: Partial<AdditionalInfoDTO> = {
        assembly_required: true,
        assembly_time: "30분",
      };

      const errors = await validateDTO(validAdditionalInfo);

      expect(errors).toHaveLength(0);
    });

    it("필수 필드가 누락된 경우 검증 실패", async () => {
      const invalidAdditionalInfo = {
        assembly_time: "30분",
      };

      const errors = await validateDTO(invalidAdditionalInfo);

      expect(errors).not.toHaveLength(0);
    });

    it("assembly_time이 Matches 조건에 맞지 않는 경우 검증 실패", async () => {
      const invalidAdditionalInfo = {
        assembly_required: true,
        assembly_time: "30",
      };

      const errors = await validateDTO(invalidAdditionalInfo);

      expect(errors).not.toHaveLength(0);
    });
  });
});
