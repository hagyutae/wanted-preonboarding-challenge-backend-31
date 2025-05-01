import { Transform } from "class-transformer";

export default function BooleanString() {
  return Transform(({ value }) => value === "true");
}
