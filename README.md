# Parcial 1
### Instlación

1. Primero clonamos el repositorio y luego accedemos a la carpeta

    https://github.com/LaaSofiaa/AREP-PARCIAL1.git
   
    cd AREP-PARCIAL1

3. Luego hacemos el empaquetamiento del parcial

    mvn package

3. Luego ejecutamos
   
     a)HttpServer

       java -cp target/Parcial1-1.0-SNAPSHOT.jar edu.escuelaing.arep.HttpServer

    b)HttpFachada

       java -cp target/Parcial1-1.0-SNAPSHOT.jar edu.escuelaing.arep.HttpFachada

### Pruebas

Nos conectamos al navegador por este link: http://localhost:35000/cliente

1. Class(java.lang.Math)

   ![image](https://github.com/user-attachments/assets/f1cd0970-cec7-4cff-b0f5-013472486387)

2. invoke(java.lang.System,getenv)

   ![image](https://github.com/user-attachments/assets/45deda52-6246-485c-9f4c-15a6a237e9d2)


3. unaryInvoke(java.lang.Math,abs,int,-3)

   ![image](https://github.com/user-attachments/assets/84b8425f-56c7-4756-a338-5e9fa99f8bfa)

   ![image](https://github.com/user-attachments/assets/34dc7775-1b12-4868-864c-f68a542b5c62)

4. binaryInvoke(java.lang.Math,max,double,4.5,double,-3.7)

   ![image](https://github.com/user-attachments/assets/ada53fdc-ddfe-4455-8fef-fda7d2e7644a)
   ![image](https://github.com/user-attachments/assets/607f1dde-2216-4faa-a07f-79532dc89de4)







   

     
