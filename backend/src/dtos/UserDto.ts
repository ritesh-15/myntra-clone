import { UserInterface } from "../interfaces/UserInterface";

class UserDto {
  id: string;
  name: string | null;
  email: string;
  phoneNumber: string | null;
  isAdmin: boolean;
  isVerified: boolean;
  isActive: boolean;

  constructor(user: UserInterface) {
    this.id = user.id;
    this.name = user.name;
    this.email = user.email;
    this.phoneNumber = user.phoneNumber;
    this.isAdmin = user.isAdmin;
    this.isVerified = user.isVerified;
    this.isActive = user.isActive;
  }
}

export default UserDto;
