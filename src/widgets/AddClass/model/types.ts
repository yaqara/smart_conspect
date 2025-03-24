// model/types.ts
import { classSchema, ClassFormData } from "./schema";
export interface FormField {
  id: string;
  name: keyof ClassFormData;
  type: string;
  label: string;
  placeholder: string;
}

export const formFields: FormField[] = [
  {
    id: "subject",
    name: "subject",
    type: "text",
    label: "Предмет",
    placeholder: "Например: Математика",
  },
  {
    id: "number",
    name: "number",
    type: "text",
    label: "Номер класса",
    placeholder: "Например: 10",
  },
  {
    id: "letter",
    name: "letter",
    type: "text",
    label: "Буква класса",
    placeholder: "Например: А",
  },
];