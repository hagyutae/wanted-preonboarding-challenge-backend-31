from fastapi import APIRouter

router = APIRouter()

@router.get("/products/{id}/reviews")
def get_reviews(product_id: int):
    return {"message":"123"}

@router.post("/products/{id}/reviews")
def create_review(product_id: int):
    return {}

@router.put("/reviews/{id}")
def modify_review(review_id: int):
    return {}

@router.delete("/reviews/{id}")
def delete_review(review_id: int):
    return {}

