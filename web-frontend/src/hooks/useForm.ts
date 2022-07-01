import { ChangeEvent, FormEvent, useEffect, useState } from "react";

export const useForm = <T>(
  initialState: T,
  validator: (values: T) => T,
  callback: (values: T) => void
) => {
  const [values, setValues] = useState<T>(initialState);
  const [errors, setErrors] = useState<any>({});
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);

  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setValues({ ...values, [name]: value });
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    setErrors(validator(values));
    setIsSubmitting(true);
  };

  useEffect(() => {
    if (Object.keys(errors).length === 0 && isSubmitting) {
      callback(values);
    }
  }, [errors]);

  return { handleChange, values, handleSubmit, errors, isSubmitting };
};
