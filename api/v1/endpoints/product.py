from fastapi import APIRouter
from app.database import connection, text, get_session
from fastapi import Depends
from sqlalchemy.orm import Session
from app.models.product import Product

router = APIRouter()

@router.post("")
def create_products():
    return {"message": ""}

@router.get("")
def get_products(db: Session = Depends(get_session)):

    
    products = db.query(Product).all()
    for product in products:
        print(product.name, product.created_at)

    db.close()

    # result = connection.execute(text("SELECT * FROM products"))
    # rows = result.fetchall()
    # products = [dict(row._mapping) for row in rows]


    return {"message": products}

@router.get("/{id}")
def get_product(id: int):
    return {"message": "product ready", "id": id}

@router.put("/{id}")
def update_product(id: int):
    return {"message": "product updated", "id": id}

@router.delete("/{id}")
def delete_products(id: int):
    return {"message": "product deleted", "id": id}

@router.post("/{id}/options")
def add_options(id: int):
    return {"message": "option added", "id": id}

@router.put("/{id}/options/{optionId}")
def update_options(id: int, optionId: int):
    return {"message": "option updated", "id": id, "optionId": optionId}


@router.delete("/{id}/options/{optionId}")
def delete_options(id: int):
    return {"message": "option deleted", "id": id}

@router.post("/{id}/images")
def add_images(id: int):
    return {"message": "image added", "id": id}

