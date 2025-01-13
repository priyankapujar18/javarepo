import java.util.*;

class Car {
    String model;
    String registrationNumber;
    boolean isAvailable;

    public Car(String model, String registrationNumber, boolean isAvailable) {
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.isAvailable = isAvailable;
    }

    public void setAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Car(Model: " + model + ", Registration Number: " + registrationNumber + ", Available: " + isAvailable + ")";
    }
}

class Customer {
    String name;
    String contact;
    String driversLicense;
    List<Rental> rentalHistory;

    public Customer(String name, String contact, String driversLicense) {
        this.name = name;
        this.contact = contact;
        this.driversLicense = driversLicense;
        this.rentalHistory = new ArrayList<>();
    }

    public boolean hasValidLicense() {
        return driversLicense != null && !driversLicense.isEmpty();
    }

    public void addRental(Rental rental) {
        rentalHistory.add(rental);
    }

    @Override
    public String toString() {
        return "Customer(Name: " + name + ", Contact: " + contact + ", Drivers License: " + driversLicense + ")";
    }
}

class Rental {
    Car car;
    Customer customer;
    Date rentalDate;
    Date returnDate;
    double rentalCharges;

    public Rental(Car car, Customer customer, Date rentalDate) {
        this.car = car;
        this.customer = customer;
        this.rentalDate = rentalDate;
        this.returnDate = null;
        this.rentalCharges = 0;
    }

    public void returnCar(Date returnDate) {
        this.returnDate = returnDate;
        long duration = (returnDate.getTime() - rentalDate.getTime()) / (1000 * 60 * 60 * 24); // in days
        double chargePerDay = 50; // Assume rental charge per day is $50
        rentalCharges = duration * chargePerDay;

        // Apply late return fee if applicable
        if (duration > 7) { // Assuming the rental period is meant to be within 7 days
            rentalCharges += (duration - 7) * 20; // $20 per day for late return
        }
    }

    public double getRentalCharges() {
        return rentalCharges;
    }

    @Override
    public String toString() {
        return "Rental(Car: " + car.model + ", Customer: " + customer.name + ", Rental Date: " + rentalDate + ", Return Date: " + returnDate + ", Charges: $" + rentalCharges + ")";
    }
}

class CarRentalService {
    List<Car> cars;
    List<Customer> customers;

    public CarRentalService() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Car findAvailableCar(String model) {
        for (Car car : cars) {
            if (car.isAvailable && car.model.equals(model)) {
                return car;
            }
        }
        return null;
    }

    public boolean rentCar(Customer customer, String carModel, Date rentalDate) {
        if (!customer.hasValidLicense()) {
            System.out.println("Customer does not have a valid driver's license.");
            return false;
        }

        Car car = findAvailableCar(carModel);
        if (car == null) {
            System.out.println("Car is not available for rental.");
            return false;
        }

        // Rent the car
        car.setAvailability(false);
        Rental rental = new Rental(car, customer, rentalDate);
        customer.addRental(rental);
        System.out.println("Car rented successfully.");
        return true;
    }

    public boolean returnCar(Customer customer, String carModel, Date returnDate) {
        for (Rental rental : customer.rentalHistory) {
            if (rental.car.model.equals(carModel) && rental.returnDate == null) {
                rental.returnCar(returnDate);
                rental.car.setAvailability(true);
                System.out.println("Car returned successfully. Charges: $" + rental.getRentalCharges());
                return true;
            }
        }
        System.out.println("Rental not found for the car.");
        return false;
    }

    public void viewAvailableCars() {
        System.out.println("Available Cars:");
        for (Car car : cars) {
            if (car.isAvailable) {
                System.out.println(car);
            }
        }
    }

    public void viewRentalHistory(Customer customer) {
        System.out.println("Rental History for " + customer.name + ":");
        for (Rental rental : customer.rentalHistory) {
            System.out.println(rental);
        }
    }
}

class CarRentalSystem {
    public static void main(String[] args) {
        // Creating Car Rental Service
        CarRentalService rentalService = new CarRentalService();

        // Adding Cars
        Car car1 = new Car("Toyota Camry", "ABC123", true);
        Car car2 = new Car("Honda Civic", "XYZ456", true);
        Car car3 = new Car("Ford Mustang", "LMN789", true);
        rentalService.addCar(car1);
        rentalService.addCar(car2);
        rentalService.addCar(car3);

        // Creating Customers
        Customer customer1 = new Customer("John Doe", "555-1234", "DL12345");
        Customer customer2 = new Customer("Jane Smith", "555-5678", "DL98765");
        rentalService.addCustomer(customer1);
        rentalService.addCustomer(customer2);

        // Rent Cars
        rentalService.rentCar(customer1, "Toyota Camry", new Date());
        rentalService.rentCar(customer2, "Honda Civic", new Date());

        // View available cars after rental
        rentalService.viewAvailableCars();

        // Return Cars
        try {
            Thread.sleep(1000); // Simulate some delay for late return
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rentalService.returnCar(customer1, "Toyota Camry", new Date());
        rentalService.returnCar(customer2, "Honda Civic", new Date());

        // View Rental History
        rentalService.viewRentalHistory(customer1);
        rentalService.viewRentalHistory(customer2);
    }
}
