# Product 커넥터 등록
curl -X POST -H "Content-Type: application/json" --data @postgres-product-connector.json http://localhost:8083/connectors

# Option/Image 커넥터 등록
curl -X POST -H "Content-Type: application/json" --data @postgres-product-option-connector.json http://localhost:8083/connectors

# Category 커넥터 등록
curl -X POST -H "Content-Type: application/json" --data @postgres-category-connector.json http://localhost:8083/connectors

# Brand 커넥터 등록
curl -X POST -H "Content-Type: application/json" --data @postgres-brand-connector.json http://localhost:8083/connectors

# Seller 커넥터 등록
curl -X POST -H "Content-Type: application/json" --data @postgres-seller-connector.json http://localhost:8083/connectors

# Tag 커넥터 등록
curl -X POST -H "Content-Type: application/json" --data @postgres-tag-connector.json http://localhost:8083/connectors