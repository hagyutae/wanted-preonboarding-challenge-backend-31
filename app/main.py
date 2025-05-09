from fastapi import FastAPI, Request
from fastapi.responses import HTMLResponse
from fastapi.templating import Jinja2Templates
from api.v1.endpoints.product import router as product_router
from api.v1.endpoints.main import router as main_router
from api.v1.endpoints.category import router as category_router
from api.v1.endpoints.reviews import router as review_router

app = FastAPI()

# 라우터 등록
app.include_router(product_router, prefix="/api/products", tags=["products"])
app.include_router(main_router, prefix="/api/main", tags=["main"])
app.include_router(category_router, prefix="/api/categories", tags=["categories"])
app.include_router(review_router, prefix="/api", tags=["reviews"])

# 템플릿 디렉토리 설정
templates = Jinja2Templates(directory="templates")

# 샘플 데이터
sample_products = [
    {
        "name": "상품 A",
        "base_price": 10000,
        "currency": "KRW",
        "brand": "브랜드 A",
        "stock": 10
    },
    {
        "name": "상품 B",
        "base_price": 20000,
        "currency": "KRW",
        "brand": "브랜드 B",
        "stock": 5
    }
]

@app.get("/", response_class=HTMLResponse)
async def get_products(request: Request):
    return templates.TemplateResponse("product_list.html", {"request": request, "products": sample_products})

