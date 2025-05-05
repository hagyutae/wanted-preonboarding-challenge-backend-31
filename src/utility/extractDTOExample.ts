import { Type } from "@nestjs/common";

export default function extractDTOExample(
  dto: Type<unknown>,
  visited = new Set<Type<unknown>>(),
): Record<string, unknown> {
  if (visited.has(dto)) {
    throw new Error(`Circular reference detected in DTO: ${dto.name}`);
  }
  visited.add(dto);

  const examples: Record<string, unknown> = {};
  const properties = getModelProperties(dto.prototype as Type<unknown>);

  for (const key of properties) {
    const metadata = Reflect.getMetadata(
      "swagger/apiModelProperties",
      dto.prototype as Type<unknown>,
      key,
    );
    if (!metadata) continue;

    const { example, type, description } = metadata;

    if (example !== undefined) {
      examples[key] = example;
      continue;
    }

    try {
      if (Array.isArray(type) && typeof type[0] === "function") {
        const nested = extractDTOExample(type[0] as Type<unknown>, new Set(visited));
        examples[key] = [nested];
      } else if (typeof type === "function") {
        const nested = extractDTOExample(type as Type<unknown>, new Set(visited));
        examples[key] = nested;
      } else {
        examples[key] = description ?? null;
      }
    } catch (err) {
      examples[key] = Array.isArray(type) ? [description ?? null] : (description ?? null);
    }
  }

  return examples;
}

function getModelProperties(prototype: Type<unknown>): string[] {
  const DECORATORS_PREFIX = "swagger";
  const API_MODEL_PROPERTIES_ARRAY = `${DECORATORS_PREFIX}/apiModelPropertiesArray`;

  const properties = Reflect.getMetadata(API_MODEL_PROPERTIES_ARRAY, prototype) || [];

  const isFunction = (val: any): val is (...args: any[]) => any => typeof val === "function";
  const isString = (val: any): val is string => typeof val === "string";

  return properties
    .filter(isString)
    .filter((key: string) => key.charAt(0) === ":" && !isFunction(prototype[key]))
    .map((key: string) => key.slice(1));
}
