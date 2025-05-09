from sqlalchemy import Column, Integer, BigInteger, Text, String, TIMESTAMP, create_engine, ForeignKey
from sqlalchemy.sql import func
# from app.database import Base
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class Product(Base):
    __tablename__ = 'products'
    id = Column(BigInteger, primary_key=True, autoincrement=True)
    name = Column(String(255), nullable=False)
    slug = Column(String(255), unique=True, nullable=False)
    short_description = Column(String(500))
    full_description = Column(Text)
    created_at = Column(TIMESTAMP, server_default=func.now())
    updated_at = Column(TIMESTAMP, server_default=func.now(), onupdate=func.now())
    seller_id = Column(BigInteger, ForeignKey('sellers.id'))
    brand_id = Column(BigInteger, ForeignKey('brands.id'))
    status = Column(String(20), nullable=False)
    