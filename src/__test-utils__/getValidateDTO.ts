import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

export default <T extends object>(DTOClass: new () => T) =>
  async (dto: Partial<T>) => {
    const instance = plainToInstance(DTOClass, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.property);
  };
