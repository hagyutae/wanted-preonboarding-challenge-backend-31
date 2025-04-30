FROM node:20-bullseye-slim  AS builder

WORKDIR /app

COPY . .

RUN npm install

RUN npm run build

FROM node:20-bullseye-slim

WORKDIR /app

COPY --from=builder /app/dist/src /app/dist/src
COPY --from=builder /app/package*.json ./
COPY --from=builder /app/node_modules ./node_modules

EXPOSE 3000

CMD ["node", "dist/src/main.js"]
