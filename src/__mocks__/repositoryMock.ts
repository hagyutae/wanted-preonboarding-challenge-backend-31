const makeMockRepository = () => ({
  save: jest.fn(),
  saves: jest.fn(),
  find_by_id: jest.fn(),
  find_by_filters: jest.fn(),
  update: jest.fn(),
  delete: jest.fn(),
});

export const mockProductRepository = makeMockRepository();
export const mockProductDetailRepository = makeMockRepository();
export const mockProductPriceRepository = makeMockRepository();
export const mockProductCategoryRepository = makeMockRepository();
export const mockProductOptionsRepository = makeMockRepository();
export const mockProductOptionGroupRepository = makeMockRepository();
export const mockProductImageRepository = makeMockRepository();
export const mockProductTagRepository = makeMockRepository();
export const mockReviewRepository = makeMockRepository();
export const mockCategoryRepository = makeMockRepository();
export const mockBrowsingRepository = makeMockRepository();

export const mockRepositoryProviders = [
  {
    provide: "IProductRepository",
    useValue: mockProductRepository,
  },
  {
    provide: "IProductDetailRepository",
    useValue: mockProductDetailRepository,
  },
  {
    provide: "IProductPriceRepository",
    useValue: mockProductPriceRepository,
  },
  {
    provide: "IProductCategoryRepository",
    useValue: mockProductCategoryRepository,
  },
  {
    provide: "IProductOptionsRepository",
    useValue: mockProductOptionsRepository,
  },
  {
    provide: "IProductOptionGroupRepository",
    useValue: mockProductOptionGroupRepository,
  },
  {
    provide: "IProductImageRepository",
    useValue: mockProductImageRepository,
  },
  {
    provide: "IProductTagRepository",
    useValue: mockProductTagRepository,
  },
  {
    provide: "IReviewRepository",
    useValue: mockReviewRepository,
  },
  {
    provide: "ICategoryRepository",
    useValue: mockCategoryRepository,
  },
  {
    provide: "IBrowsingRepository",
    useValue: mockBrowsingRepository,
  },
];
