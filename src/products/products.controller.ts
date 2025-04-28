import {
  Controller,
  Get,
  //   Post,
  //   Body,
  //   Patch,
  //   Param,
  //   Delete,
  Query,
} from '@nestjs/common';
// import { ProductsService } from './products.service';
// import { CreateProductDto } from './dto/create-product.dto';
// import { UpdateProductDto } from './dto/update-product.dto';
import { PaginationQueryDto } from './dto/pagination-query.dto';
import { ProductsService } from './products.service';

@Controller('products')
export class ProductsController {
  constructor(private readonly productsService: ProductsService) {}

  //   @Post()
  //   create(@Body() createProductDto: CreateProductDto) {
  //     return this.productsService.create(createProductDto);
  //   }

  @Get()
  findAll(@Query() paginationQuery: PaginationQueryDto) {
    console.log('🔴 paginationQuery', paginationQuery.category.length);
    return this.productsService.findAll(paginationQuery);
  }

  //   @Get(':id')
  //   findOne(@Param('id') id: string) {
  //     return this.productsService.findOne(id);
  //   }

  //   @Patch(':id')
  //   update(@Param('id') id: string, @Body() updateProductDto: UpdateProductDto) {
  //     return this.productsService.update(id, updateProductDto);
  //   }

  //   @Delete(':id')
  //   remove(@Param('id') id: string) {
  //     return this.productsService.remove(id);
  //   }
}
