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

  toJson() {
    return {
      id: this.id,
      name: this.name,
      email: this.email,
      phoneNumber: this.phoneNumber,
      isAdmin: this.isAdmin,
      isVerified: this.isVerified,
      isActive: this.isActive,
    };
  }
}

export default UserDto;
