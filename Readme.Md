This is maven based project

Step to run application


1. Run mvn clean install in root of the project
2. give permission to script (parking_lot)

    **chmod 775 parking_lot**

3. To run in file input mode run below command

    **parking_lot $file_path**
    where $file_path is path of input file

4. To run in console mode run below command
    **parking_lot**


**P.S :**
1. Type X or blank line will quit the program
2. In case of invalid query is supplied application will exit as there is no benifit of running application when an invalid input is provided input. we can modify the implemenation in case changes are required
