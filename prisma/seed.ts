import { PrismaClient } from '@prisma/client';
import * as fs from 'fs';
import * as path from 'path';

const prisma = new PrismaClient();

async function executeSqlFile(filePath: string) {
  const sql = fs.readFileSync(filePath, 'utf8');
  const commands = sql.split(';').filter((cmd) => cmd.trim());

  for (const command of commands) {
    if (command.trim()) {
      await prisma.$executeRawUnsafe(command);
    }
  }
}

async function main() {
  try {
    // SQL 파일들을 순서대로 실행
    const sqlFiles = [
      'users.sql',
      'sellers.sql',
      'brands.sql',
      'categories.sql',
      'tags.sql',
      'products.sql',
      'product_extended.sql',
      'product_options.sql',
      'reviews.sql',
    ];

    for (const file of sqlFiles) {
      const filePath = path.join(__dirname, '..', 'data', file);
      await executeSqlFile(filePath);
      console.log(`Executed ${file} successfully`);
    }

    console.log('All data has been seeded successfully');
  } catch (error) {
    console.error('Error seeding data:', error);
    throw error;
  } finally {
    await prisma.$disconnect();
  }
}

main().catch((e) => {
  console.error(e);
  process.exit(1);
});
