export interface UserInterface {
  id: string;
  name: string | null;
  email: string;
  phoneNumber: string | null;
  isAdmin: boolean;
  isVerified: boolean;
  isActive: boolean;
}
