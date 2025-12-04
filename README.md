# Menu-Driven-Java-Banker-s-Algorithm-Tool
## Getting Started: Setting up the tool
The tool is available in two versions, GUI & CLI. To download and use your preferred version, follow the instructions bellow 
### GUI
#### Usage Guide
1.	Download the tool from the GitHub repository by following the link https://github.com/Amal-Alassaf/Menu-Driven-Java-Banker-s-Algorithm-Tool-.git
3.	Place the two files in one package using your preferred IDE (e.g. NetBeans, Eclipse or visual studio code) and run the tool. 
4.	Start using the tool as the interface suggests.
#### Example Input/Output
<img width="1280" height="912" alt="image" src="https://github.com/user-attachments/assets/c3b51f40-7ae9-4fa6-bc65-a931224451da" />

### CLI
#### Usage Guide
1.	Download the tool from the GitHub repository by following the link https://github.com/Amal-Alassaf/Menu-Driven-Java-Banker-s-Algorithm-Tool-.git
2.	Make sure you have (Java 8.2 JDK, NetBeans and Picocli library)
   * install picocli by following the link: https://repo1.maven.org/maven2/info/picocli/picocli/
3. Place the two files in one package using your preferred IDE (e.g. NetBeans, Eclipse or visual studio code) and run the tool. 
4. Open the project package.
5. Right-click on the project folder and choose properties. Then, from the menu on the left, click on library, add the jar for the picocli (in this project we used version 4.7.5).
6. Within the project file, click on "Run" in the top bar, select "Clean and Build," and then copy the project's JAR URL.
7. Paste the generated URL into your terminal and start using the tool based on the insturctions printed on the terminal.
#### Example Input/Output
<img width="1280" height="675" alt="image" src="https://github.com/user-attachments/assets/a9ec5499-8ffe-4008-a5b9-e20e23d56969" />
<img width="1280" height="636" alt="image" src="https://github.com/user-attachments/assets/067ed144-410a-4685-a0dd-7cde71f7d4d9" />

## Troubleshooting 
Suggestion and reporting of issues are welcomed through the GitHub “ISSUE_TAMPLATE”.

## Detailed Description of the Code 
### System Initialization Methods
* `menu()`: presents the user with the main options (Initialize, Display, Request, Release, Exit) and calls the appropriate handler methods based on the user's choice.
* `getNumberOfProcesses()`: prompts the user to enter and validates the number of processes or threads (P) in the system.
* `getNumberOfResources()`: prompts the user to enter and validates the number of resource types (R) available in the system.
* `readAvailable()`: collects and validates the initial number of instances for each resource type.
* `readMaxMatrix()`: collects and validates the maximum demand matrix, which defines the maximum required resources for each process to complete.
* `AllocationMatrix()`: collects the resources currently allocated to each process. It aldo ensures that the allocated resources for any resource does not exceed the maximum demand.
* `calculateNeedMatrix()`: computes the Need matrix using the formula: Need = Max - Allocation. which represents the remaining resources each process needs.
* `printInitialState()`: prints the system's current state (Available, Allocation, Max, and Need matrices) in a table-like formatز
* `saftyCheck()`: executes the Banker's safety algorithm to determine if the current state is safe. If safe, it calculates and prints a valid safe sequence of processes..
### Resource Handling Methods (Menu Options 3 and 4)
* `handleRequest()`: collects the process ID and the resource request vector from the user, validates input, and then calls requestResources to execute the request.
* `requestResources()`: 
  1. Checks if the request exceeds the process's Need.
  2. Checks if the request exceeds the Available resources.
  3. If checks pass, it pretends to allocate the resources by updating the state matrices.
  4. Calls saftyCheck.
  5. If safe, the allocation is approved; if unsafe, the request is denied.
* `handleRelease()`: collects the process ID and the release vector from the user, validates input, and then calls releaseResources to execute the request.
* `releaseResources()`: updates the system state by adding the released resources to the Available vector, subtracting them from the process's Allocation, and adding them back to the process's Need

## Group Members & their Roles
| Member | Role | 
| -------- | -------- |
| Anfal Basim Barri 4455123| Interface design & implementation |
| Aram Nayyaf Alruwaythi 4453447 | Content 5 |
| Alaa Hassan Alqulayti 4455238 | Content 5 |
| Danah Abdulaziz Alhejaili 4453310 | Content 5 |
| Amal Saad Al-Assaf 4451352 | `saftyCheck` algorithm implementation, Report & README file format |
