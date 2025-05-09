from fastapi import APIRouter

router = APIRouter()

@router.get("")
def create_products():
    return {"message": "category list"}

@router.get("/{id}/products")
def create_products(id: int):
    return {"message": "category's products", "id": id}

