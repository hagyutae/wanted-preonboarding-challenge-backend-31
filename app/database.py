from sqlalchemy import create_engine, text
from sqlalchemy.orm import sessionmaker
from app.models.product import Product

# 데이터베이스 엔진  생성
DB_URL = "postgresql://root:root@db:5432/wanted"
engine = create_engine(DB_URL, echo=True)

try:
    connection = engine.connect()
    print("DB 연결 성공")
except Exception as e:
    print("DB 연결 실패:", e)

Session = sessionmaker(bind=engine)

# 세션 생성
def get_session():    
    db = Session()
    try:
        yield db
    finally:
        db.close()

# products = session.query(Product).all()
# for product in products:
#     print(product.name, product.created_at)

# session.close()