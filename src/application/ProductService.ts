import { Inject, Injectable } from "@nestjs/common";

import ResponseDTO from "src/interfaces/dto/ResponseDTO";
import IRepository from "src/infrastructure/IRepository";

@Injectable()
export default class ProductService {
  constructor(
    @Inject("IRepository")
    private repository: IRepository<any>,
  ) {}

  async create(data: any) {
    const product = await this.repository.create(data);

    const response = {
      success: true,
      data: product,
      message: "상품이 성공적으로 등록되었습니다.",
    } as ResponseDTO;

    return response;
  }

  async getAll() {
    await this.repository.findAll();
  }

  async getById(id: string) {
    const product = await this.repository.findById(id);

    const response = {
      success: true,
      data: product,
      message: "상품 상세 정보를 성공적으로 조회했습니다.",
    } as ResponseDTO;

    return response;
  }

  async update(id: string, data: any) {
    const product = await this.repository.update(id, data);

    const response = {
      success: true,
      data: product,
      message: "상품이 성공적으로 수정되었습니다.",
    } as ResponseDTO;

    return response;
  }

  async delete(id: string) {
    await this.repository.delete(id);

    const response = {
      success: true,
      data: null,
      message: "상품이 성공적으로 삭제되었습니다.",
    } as ResponseDTO;

    return response;
  }

  async addOptions(id: string, option: any) {
    const product = await this.repository.findById(id);

    const updatedProduct = {
      ...product,
      option_groups: [...(product?.option_groups || []), option],
    };

    await this.repository.update(id, updatedProduct);

    const response = {
      success: true,
      data: updatedProduct,
      message: "상품 옵션이 성공적으로 추가되었습니다.",
    } as ResponseDTO;

    return response;
  }

  async updateOptions(id: string, optionId: string, option: any) {
    const product = await this.repository.findById(id);

    const updatedProduct = {
      ...product,
      option_groups: product?.option_groups.map((opt: any) =>
        opt.id === option.id ? option : opt,
      ),
    };

    await this.repository.update(id, updatedProduct);

    const response = {
      success: true,
      data: updatedProduct,
      message: "상품 옵션이 성공적으로 수정되었습니다.",
    } as ResponseDTO;

    return response;
  }

  async deleteOptions(id: string, optionId: string) {
    const product = await this.repository.findById(id);

    const updatedProduct = {
      ...product,
      option_groups: product?.option_groups.filter((opt: any) => opt.id !== id),
    };

    await this.repository.update(id, updatedProduct);

    const response = {
      success: true,
      data: null,
      message: "상품 옵션이 성공적으로 삭제되었습니다.",
    } as ResponseDTO;

    return response;
  }

  async addImages(id: string, body: any) {
    const product = await this.repository.findById(id);

    const updatedProduct = {
      ...product,
      images: [...(product?.images || []), body],
    };

    await this.repository.update(id, updatedProduct);

    const response = {
      success: true,
      data: updatedProduct,
      message: "상품 이미지가 성공적으로 추가되었습니다.",
    } as ResponseDTO;

    return response;
  }
}
