import java.util.*;
import java.io.*;

// Main menu of employee management system
class MainMenu {
    public void menu() {
        System.out.println("\t\t----------------------------------------------");
        System.out.println();
        System.out.println("\t\t     WELCOME TO EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println();
        System.out.println("\t\t----------------------------------------------");
        System.out.println();
        System.out.println("\t\t\t    ********************");
        System.out.println();
        System.out.println("\t\t\t      Krishna Balasara  ");
        System.out.println();
        System.out.println("\t\t\t    ********************");
        System.out.println("\n\nPress 1 : For Add an Employee Details");
        System.out.println("Press 2 : For See an Employee Details");
        System.out.println("Press 3 : For Remove an Employee");
        System.out.println("Press 4 : For Update Employee Details");
        System.out.println("Press 5 : For Exit the EMS Portal");
    }
}

// to add employee details
class ADD_EMP {
    public void createFile() {
        Scanner sc = new Scanner(System.in);

        EMP_Details emp = new EMP_Details();
        emp.getInfo();
        try {
            File f1 = new File("file" + emp.employee_id + ".txt");
            if (f1.createNewFile()) {
                FileWriter myWriter = new FileWriter("file" + emp.employee_id + ".txt");
                myWriter.write("Employee ID:" + emp.employee_id + "\n" +
                        "Full Name         :" + emp.fullName + "\n" +
                        "Employee Contact  :" + emp.employee_contact + "\n" +
                        "Email Information :" + emp.email + "\n" +
                        "Position          :" + emp.position + "\n" +
                        "Experience (years):" + emp.experience + "\n" +
                        "Employee Salary   :" + emp.employee_salary);
                myWriter.close();
                System.out.println("\nEmployee Added Successfully :)\n");

                System.out.print("\nPress Enter to Continue...");
                sc.nextLine();
            } else {
                System.out.println("\nSorry ! This Employee already exists :(");
                System.out.print("\nPress Enter to Continue...");
                sc.nextLine();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

// get Details
class EMP_Details {
    String fullName;
    String email;
    String position;
    String employee_id;
    String employee_salary;
    String employee_contact;
    String experience;

    public void getInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Employee's full name ---: ");
        fullName = sc.nextLine();
        System.out.print("Enter Employee's ID ----------: ");
        employee_id = sc.nextLine();
        System.out.print("Enter Employee's Email ID ----: ");
        email = sc.nextLine();
        System.out.print("Enter Employee's Position ----: ");
        position = sc.nextLine();
        System.out.print("Enter Employee contact Info --: ");
        employee_contact = sc.nextLine();
        System.out.print("Enter Employee's Salary ------: ");
        employee_salary = sc.nextLine();
        System.out.print("Enter Employee's Experience --: ");
        experience = sc.nextLine();
    }
}

//show details
class EMP_show
{
  public void viewFile(String s) throws Exception
  {
    File file = new File("file"+s+".txt");
    Scanner sc = new Scanner(file);

    while (sc.hasNextLine())
     {
       System.out.println(sc.nextLine());
     }
   }
}

// remove employee
class Rmv_EMP
{
    public void removeFile(String ID)
    {

    File file = new File("file"+ID+".txt");
      if(file.exists())
       {
         if(file.delete());
         {
           System.out.println("\nEmployee has been removed Successfully...!");
         }
       }
      else
       {
            System.out.println("\nSorry! Employee does not exists :( ");
       }
     }
}

//Update details
class Update_EMP
{
  public void updateFile(String s,String o,String n) throws IOException
  {
   File file = new File("file"+s+".txt");
   Scanner sc = new Scanner(file);
   String fileContext="";
   while (sc.hasNextLine())
       {
         fileContext =fileContext+"\n"+sc.nextLine();
       }
   FileWriter myWriter = new FileWriter("file"+s+".txt");
   fileContext = fileContext.replaceAll(o,n);
   myWriter.write(fileContext);
   myWriter.close();
  }
}

// exit
class Exit
{
  public void out()
  {
    System.out.println("\n\t\t*********************************");
    System.out.println();
    System.out.println("\t\tThank You...!!! Have a Good day :) ");
    System.out.println("\n\t\t*********************************");
    System.out.println();
    System.out.println("\t\t - Created by Krishna Balasara\n");
    System.exit(0);
  }
}

//main class
class CRUD {
    public static void main(String arv[])
  {
    // To clear the output Screen 
    System.out.print("\033[H\033[2J");

    Scanner sc=new Scanner(System.in);
    EMP_show epv =new EMP_show();

    int i=0;

    MainMenu obj1 = new MainMenu();
    obj1.menu();

    while(i<6)
    {

      System.out.print("\nPlease Enter Your choice :");
      i=Integer.parseInt(sc.nextLine());

      switch(i)
      {
        case 1:
        {
        
        ADD_EMP ep =new ADD_EMP();
        ep.createFile();

        System.out.print("\033[H\033[2J");
        obj1.menu();
        break;
        }
        case 2:
        {
          System.out.print("\nPlease Enter Employee's ID :");
          String s=sc.nextLine();
          try
          {
            epv.viewFile(s);}
            catch(Exception e){System.out.println(e);}

            System.out.print("\nPress Enter to Continue...");
            sc.nextLine();
            System.out.print("\033[H\033[2J");
            obj1.menu();
            break;
          }

        case 3:
        {
          System.out.print("\nPlease Enter Employee's ID :");
          String s=sc.nextLine();
          Rmv_EMP epr =new Rmv_EMP();
          epr.removeFile(s);

          System.out.print("\nPress Enter to Continue...");
          sc.nextLine();
          System.out.print("\033[H\033[2J");
          obj1.menu();
          break;
        }
        case 4:
        {
            System.out.print("\nPlease Enter Employee's ID :");
            String I=sc.nextLine();
            try
            {
              epv.viewFile(I);
            }
            catch(Exception e)
            {
              System.out.println(e);
            }
            Update_EMP epu = new Update_EMP();
            System.out.print("Please Enter the detail you want to Update :");
    	      System.out.print("\nFor Example :\n");
            System.out.println("If you want to Change the Name, then Enter Current Name and Press Enter. Then write the new Name then Press Enter. It will Update the Name.\n");
            String s=sc.nextLine();
            System.out.print("Please Enter the Updated Info :");
            String n=sc.nextLine();
            try
            {
              epu.updateFile(I,s,n);

              System.out.print("\nPress Enter to Continue...");
              sc.nextLine();
              System.out.print("\033[H\033[2J");
              obj1.menu();
              break;
            }
            catch(IOException e)
            {
              System.out.println(e);
            }
        }
        case 5:
        {
          Exit obj = new Exit();
          obj.out();
        }
      }
    }
  }
}
