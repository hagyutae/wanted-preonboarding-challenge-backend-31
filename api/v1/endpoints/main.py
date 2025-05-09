from fastapi import APIRouter

router = APIRouter()

@router.get("")
def create_products():
    return {"message": "신규 + 카테고리별 인기상품"}

