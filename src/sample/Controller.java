package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller {
    private ArrayList<Vertices> VerticeList = new ArrayList<>();
    private ArrayList<Arcs> ArcssList= new ArrayList<>();
    public GraphicsContext gc;
    private String tool_selected="None";
    public double click_x;
    public double click_y;
    private int number_vertice=-1;
    public Vertices vertices;
    public Vertices vertices_selected_from;
    public Vertices vertices_selected_to;
    public Arcs arcs;
    public int choose_detector=0;
    double x1=0;
    double x2=0;
    double y1=0;
    double y2=0;
    int p=0; //Количество вершин
    int p1=0;// Вершина1
    int p2=0; //Вершина2
    int q1=0;// Вершина1
    int q2=0; //Вершина2
    int leng = 1;//первоначальная степень матрицы смещности
    int q=0;// Количество ребер
    int[][] matrix_smeghnost;
    int radius_graph=100000;// радиус графа
    int vertice_1;
    int vertice_2;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea textArea;

    @FXML
    private RadioButton add_vertices;

    @FXML
    private ToggleGroup tool_choose;

    @FXML
    private RadioButton add_arc;

    @FXML
    private TextField vertice_from;

    @FXML
    private TextField vertice_to;

    @FXML
    private Button find_button;

    @FXML
    private Button delete_button;

    @FXML
    private TextField vertice_from1;

    @FXML
    private TextField vertice_to1;

    @FXML
    private Button Center_button;

    @FXML
    private Button articulation_point;

    @FXML
    private Button bridge_button;

    @FXML
    private Canvas canvas;

    @FXML
    void Bridge_function(ActionEvent event) {
        if(1==1) {
            create_matrix();
            show_matrix(p, matrix_smeghnost);
            textArea.appendText("\n1) Для нахождения мостов \nв цикле рассмотрим каждую ребро,\n" +
                    "и удалим данную ребро из нашей\n матрица. В полученной матрице \n" +
                    "вычилсим расстояние между\n всеми вершинами. Если расстояние \n" +
                    "между некоторыми вершинами \n стало 0, то наш граф разделена\nна блоки, и следовательно,\n вершина является точкой сочления\n" +
                    "мостом\n\n");
            for (Vertices vertices : VerticeList) {
                vertices.setColor(Color.BLACK);
                vertices.show(gc);
            }
            for (Arcs arcs : ArcssList) {
                arcs.setColor(Color.BLACK);
                arcs.show(gc);
            }


            int[][] matr = new int[p][p];
            for (int i = 0; i < p; i++){     //создание копии матрицы смежности для дальнейшей работы с ней
                for (int j = 0; j < p; j++)
                    matr[i][j] = matrix_smeghnost[i][j];
            }

            for(int v=0; v<q; v++) {
                for (int i = 0; i < p; i++)     //переопределение массива matr
                    for (int j = 0; j < p; j++)
                        matr[i][j] = matrix_smeghnost[i][j];
                textArea.appendText("Рассмотрим ребро №"+v+"\n");
                textArea.appendText("При удалении данного ребра,матрица смежности имеет вид\n");

                    vertice_1=arcs.getFrom_vertice();
                    vertice_2=arcs.getTo_vertice();
                    matr[vertice_1][vertice_2] = 0;
                    matr[vertice_2][vertice_1] = 0;

                for (int i = 0; i < p; i++) {
                    for (int j = 0; j < p; j++)
                        textArea.appendText("\t"+matr[i][j]);
                    textArea.appendText("\n");
                }    //создание копии матрицы смежности для окончательного результата



                int[][] result = new int[p][p];
                for (int i = 0; i < p; i++)     //создание копии матрицы смежности для окончательного результата
                    for (int j = 0; j < p; j++)
                        result[i][j] = matr[i][j];


                for (int t = 2; t <= q; t++)   //возведение матрицы смежности в нужную степень
                {
                    int[][] mat = new int[p][p];
                    for (int i = 0; i < p; i++) //инициализация матрицы matr нулевыми значениями
                        for (int j = 0; j < p; j++)
                            mat[i][j] = 0;

                    for (int i = 0; i < p; i++)     //перемножение матриц
                    {
                        for (int j = 0; j < p; j++) {
                            int sum = 0;
                            for (int k = 0; k < p; k++) {
                                sum += matr[i][k] * matr[k][j];
                            }
                            mat[i][j] = sum;
                        }
                    }

                    for (int i = 0; i < p; i++)     //перенос временных значений из массива mat  в массив matr
                        for (int j = 0; j < p; j++) {
                            matr[i][j] = mat[i][j];
                            if (matr[i][j] != 0 && result[i][j] == 0 && i != j)
                                result[i][j] = t;
                        }

                }
                textArea.appendText("Расстояние между вершинами\n");

                for (int i = 0; i < p; i++)     //вывод матрицы смежности в степени lg на экран
                {
                    for (int j = 0; j < p; j++)
                        textArea.appendText("\t" + result[i][j]);
                    textArea.appendText("\n");
                }
                for (int j = 0; j < p; j++) {
                    if (v<p){
                        result[v][j] = 1;
                        result[j][v] = 1;
                    }//Заполняем единичками, потому что они эти вершини мы удалили и следовательно на проверку они не должни попасть

                }
                for (int i = 0; i < p; i++)
                {
                    for (int j = 0; j < p; j++){
                        if (result[i][j] ==0 && i!=j ){
                            ArcssList.get(v).setBridge(true);
                            break;
                        }
                    }

                }


            }
            textArea.appendText("Мосты, ребра с номерами вершин:\n");
            for (Arcs arcs : ArcssList) {
                if(arcs.isBridge()==true){
                    arcs.setColor(Color.RED);
                    arcs.show(gc);
                    System.out.println(arcs.getColor());
                    textArea.appendText("\t"+"("+arcs.from_vertice+","+arcs.to_vertice+")");
                }
            }

        }



    }

    @FXML
    void Centroid_function(ActionEvent event) {
        if(1==1) {
            /*p1 = Integer.parseInt(vertice_from.getText());
            p2 = Integer.parseInt(vertice_to.getText());
            q1 = Integer.parseInt(vertice_from1.getText());
            q2 = Integer.parseInt(vertice_to1.getText());*/
            create_matrix();
            show_matrix(p, matrix_smeghnost);
            for (Vertices vertices : VerticeList) {
                vertices.setColor(Color.BLACK);
                vertices.show(gc);
            }
            for (Arcs arcs : ArcssList) {
                arcs.setColor(Color.BLACK);
                arcs.show(gc);
            }
            textArea.appendText("\n1) Находим расстояние между \nвсеми вершинами\n");

            int[][] matr = new int[p][p];
            for (int i = 0; i < p; i++){     //создание копии матрицы смежности для дальнейшей работы с ней
                for (int j = 0; j < p; j++)
                    matr[i][j] = matrix_smeghnost[i][j];
            }
            int[] long_path=new int[p];// Массив для сохранение длинных путей

            int[][] result = new int[p][p];
            for (int i = 0; i < p; i++)     //создание копии матрицы смежности для окончательного результата
                for (int j = 0; j < p; j++)
                    result[i][j] = matrix_smeghnost[i][j];

            for (int i = 0; i < p; i++)     //переопределение массива matr
                for (int j = 0; j < p; j++)
                    matr[i][j] = matrix_smeghnost[i][j];

            for (int t=2; t<=q; t++)   //возведение матрицы смежности в нужную степень
            {
                int[][] mat = new int[p][p];
                for (int i = 0; i < p; i++) //инициализация матрицы matr нулевыми значениями
                    for (int j = 0; j < p; j++)
                        mat[i][j] = 0;

                for (int i = 0; i < p; i++)     //перемножение матриц
                {
                    for (int j = 0; j < p; j++)
                    {
                        int sum = 0;
                        for (int k = 0; k < p; k++)
                        {
                            sum += matr[i][k] * matrix_smeghnost[k][j];
                        }
                        mat[i][j] = sum;
                    }
                }

                for (int i = 0; i < p; i++)     //перенос временных значений из массива mat  в массив matr
                    for (int j = 0; j < p; j++){
                        matr[i][j] = mat[i][j];
                        if (matr[i][j]!=0&&result[i][j]==0 && i!=j)
                            result[i][j]=t;
                    }

            }
            for (int i = 0; i < p; i++)     //вывод матрицы смежности в степени lg на экран
            {
                for (int j = 0; j < p; j++)
                    textArea.appendText("\t" + result[i][j]);
                textArea.appendText("\n");
            }
            int long_path_vertice=0;
            for (int i = 0; i < p; i++)     //Нахождения саммых длинных путей
            {
                for (int j = 0; j < p; j++)
                    if(result[i][j]>long_path_vertice)
                        long_path_vertice=result[i][j];
                long_path[i]=long_path_vertice;
                VerticeList.get(i).setLong_path(long_path_vertice);
                long_path_vertice=0;
            }
            textArea.appendText("Находим саммые длинные пути из вершин\n");
            for(int i=0; i<p; i++){
                textArea.appendText(long_path[i]+"\n");

            }
            textArea.appendText("Радиус графа равен:\t");
            for(int i=0; i<p; i++){
                if(long_path[i]<radius_graph)
                    radius_graph=long_path[i];
            }
            textArea.appendText(radius_graph+"\n");
            textArea.appendText("Центры графа:(Отмечены красным цветом)\n");
            textArea.appendText("Вершини с номерами");


            for (Vertices vertices : VerticeList) {
                if (vertices.getLong_path()==radius_graph){
                    vertices.setColor(Color.RED);
                    vertices.show(gc);
                    textArea.appendText("\t"+vertices.getNumber_vertice());



                }

            }


        }

    }

    @FXML
    void Delete_function(ActionEvent event) {
        p1 = 0;
        p2 = 0;
        q1 = 0;
        q2 = 0;
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 1300, 700);
        VerticeList.clear();
        ArcssList.clear();
        choose_detector=0;
        number_vertice=-1;
        choose_detector=0;
        x1=0;
        x2=0;
        y1=0;
        y2=0;
        p=0; //Количество вершин
        leng = 1;//первоначальная степень матрицы смещности
        q=0;// Количество ребер


    }

    @FXML
    void Find_path_function(ActionEvent event) {
        if(!(vertice_from.getText().equals("")&&vertice_to.getText().equals("")&&vertice_from1.getText().equals("")&&vertice_to1.getText().equals(""))) {
            p1 = Integer.parseInt(vertice_from.getText());
            p2 = Integer.parseInt(vertice_to.getText());
            q1 = Integer.parseInt(vertice_from1.getText());
            q2 = Integer.parseInt(vertice_to1.getText());
            create_matrix();
            show_matrix(p, matrix_smeghnost);
            for (Vertices vertices : VerticeList) {
                vertices.setColor(Color.BLACK);
                vertices.show(gc);
            }
            for (Arcs arcs : ArcssList) {
                arcs.setColor(Color.BLACK);
                arcs.show(gc);
            }
            VerticeList.get(p1).setColor(Color.BLUE);
            VerticeList.get(p1).show(gc);

            VerticeList.get(p2).setColor(Color.BLUE);
            VerticeList.get(p2).show(gc);

            VerticeList.get(q2).setColor(Color.PURPLE);
            VerticeList.get(q2).show(gc);

            VerticeList.get(q1).setColor(Color.PURPLE);
            VerticeList.get(q1).show(gc);


            textArea.appendText("\n1) Для нахождения расстояние между \nвершинами " + p1 + " и " + p2 + ", которые\nрасскрасены синим цветом,\n необходимо возвести \n матрицу А во 2 степень");

            int[][] matr = new int[p][p];
            for (int i = 0; i < p; i++)     //создание копии матрицы смежности для дальнейшей работы с ней
                for (int j = 0; j < p; j++)
                    matr[i][j] = matrix_smeghnost[i][j];
            boolean bTrue = true;  //признак того, что расстояние найдено

            while (bTrue)      //цикл для нахождения расстояние между вершинами
            {
                int[][] mat = new int[p][p]; //создание временного массива
                for (int i = 0; i < p; i++)
                    for (int j = 0; j < p; j++)
                        mat[i][j] = 0;
                textArea.appendText("\nМатрица смежности в " + (leng + 1) + " степени:\n");
                for (int i = 0; i < p; i++)     //возведение матрицы смежности в стпень
                {
                    for (int j = 0; j < p; j++) {
                        int sum = 0;
                        for (int k = 0; k < p; k++) {
                            sum += matr[i][k] * matrix_smeghnost[k][j];
                        }
                        mat[i][j] = sum;
                    }
                }
                for (int i = 0; i < p; i++)     //копирование значений элементов из массива mat в matr
                    for (int j = 0; j < p; j++)
                        matr[i][j] = mat[i][j];

                leng++;
                for (int i = 0; i < p; i++) //ввывод матрицы смежности в степени leng на экран
                {
                    for (int j = 0; j < p; j++)
                        textArea.appendText("\t" + matr[i][j]);
                    textArea.appendText("\n");
                }
                if (matr[p1][p2] != 0)  //если расстояние найдено, то устанавливаем признак завершение работы цикла
                    bTrue = false;
                else textArea.appendText("\tТак как значение на пересечении "+p1+" \nстроки и "+p2+" столбца равно 0, " +
                        "значит между  \nвершинами"+p1+" и "+p2+" нет маршрута длинной "+leng+".\n" +
                        "Следовательно нужно возвезсти изначаль-\nную матрицу А в "+(leng+1)+" степень.\n");
            }
            textArea.appendText("\tИз матрицы видно, что значение на \nпересечении"+p1+" строки и "+p2+" столбца не равно 0,\n" +
                    "значит расстояние между искомыми \nвершинами равно степени матрицы, т.е "+leng);

            int lg = q - p + 3;
            textArea.appendText("\n2)Для нахождения количества маршрутов \nдлины "+lg+" между вершинами "+q1+" и "+q2+",\n которые расскрасани фиолет цветом\nнеобходимо " +
                    "возвести матрицу А в "+lg+" степень\n");

            textArea.appendText("Матрица смежности в "+lg+" степени:\n");
            for (int i = 0; i < p; i++)     //переопределение массива matr
                for (int j = 0; j < p; j++)
                    matr[i][j] = matrix_smeghnost[i][j];

            for (int t=2; t<=lg; t++)   //возведение матрицы смежности в нужную степень
            {
                int[][] mat = new int[p][p];
                for (int i = 0; i < p; i++) //инициализация матрицы matr нулевыми значениями
                    for (int j = 0; j < p; j++)
                        mat[i][j] = 0;

                for (int i = 0; i < p; i++)     //перемножение матриц
                {
                    for (int j = 0; j < p; j++)
                    {
                        int sum = 0;
                        for (int k = 0; k < p; k++)
                        {
                            sum += matr[i][k] * matrix_smeghnost[k][j];
                        }
                        mat[i][j] = sum;
                    }
                }

                for (int i = 0; i < p; i++)     //перенос временных значений из массива mat  в массив matr
                    for (int j = 0; j < p; j++)
                        matr[i][j] = mat[i][j];
            }

            for (int i = 0; i < p; i++)     //вывод матрицы смежности в степени lg на экран
            {
                for (int j = 0; j < p; j++)
                    textArea.appendText("\t" + matr[i][j]);
                textArea.appendText("\n");
            }
            int count = matr[q1][q2];
            textArea.appendText("\tДля нахождения количества маршрутов \nдлинной "+lg+" между вершинами "+q1+" и "+q2+",\n" +
                    "достаточно найти значение матрицы \nсмежности на пересечении данных строки \nи столбца.\n" +
                    "В Нашем случае количество маршрутов \nравно: "+count);

            textArea.appendText("\nОтвет: \n1) Расстояние между вершинами \n"+p1+" и "+p2+" равно "+leng+";\n" +    //запись ответа
                    "2) Количество маршрутов длииной "+lg+" \n между вершинами "+q1+" и "+q2+" равно "+count+".");

        }



    }
    void create_matrix(){
        matrix_smeghnost=new int[p][p];
        for (int i=0; i<p;i++){
            for(int j=0; j<p; j++){
                matrix_smeghnost[i][j]=0;
            }
        }
        for(int i=0; i<ArcssList.size(); i++) {
            arcs=ArcssList.get(i);
            matrix_smeghnost[arcs.from_vertice][arcs.to_vertice]=1;
            matrix_smeghnost[arcs.to_vertice][arcs.from_vertice]=1;

        }

    }
    void show_matrix(int p,int[][] matrix){
        textArea.setText("\t\t"+"Матрица смежности имеет "+"\n\t\t\t"+"следующий вид:"+"\n");
        for (int i=0; i<p;i++){
            for(int j=0; j<p; j++){
                textArea.appendText("\t"+matrix[i][j]);
            }
            textArea.appendText("\n");
        }
    }

    @FXML
    void articulation_function(ActionEvent event) {
        if(1==1) {
            create_matrix();
            show_matrix(p, matrix_smeghnost);
            textArea.appendText("\n1) Для нахождения точки сочления \nв цикле рассмотрим каждую вершину,\n" +
                    "и удалим данную вершину из нашей\n матрица. В полученной матрице \n" +
                    "вычилсим расстояние между\n всеми вершинами. Если расстояние \n" +
                    "между некоторыми вершинами \n стало 0, то нащ граф разделена\nна блоки, и следовательно,\n вершина является точкой сочления\n" +
                    "точкой сочления\n\n");
            for (Vertices vertices : VerticeList) {
                vertices.setColor(Color.BLACK);
                vertices.show(gc);
            }
            for (Arcs arcs : ArcssList) {
                arcs.setColor(Color.BLACK);
                arcs.show(gc);
            }


            int[][] matr = new int[p][p];
            for (int i = 0; i < p; i++){     //создание копии матрицы смежности для дальнейшей работы с ней
                for (int j = 0; j < p; j++)
                    matr[i][j] = matrix_smeghnost[i][j];
            }

            for(int v=0; v<p; v++) {
                for (int i = 0; i < p; i++)     //переопределение массива matr
                    for (int j = 0; j < p; j++)
                        matr[i][j] = matrix_smeghnost[i][j];
                textArea.appendText("Рассмотрим вершину №"+v+"\n");
                textArea.appendText("При удалении данной вершины,матрица смежности имеет вид\n");

                for (int j = 0; j < p; j++) {
                    matr[v][j] = 0;
                    matr[j][v] = 0;
                }
                for (int i = 0; i < p; i++) {
                    for (int j = 0; j < p; j++)
                        textArea.appendText("\t"+matr[i][j]);
                    textArea.appendText("\n");
                }    //создание копии матрицы смежности для окончательного результата



                int[][] result = new int[p][p];
                for (int i = 0; i < p; i++)     //создание копии матрицы смежности для окончательного результата
                    for (int j = 0; j < p; j++)
                        result[i][j] = matr[i][j];


                for (int t = 2; t <= q; t++)   //возведение матрицы смежности в нужную степень
                {
                    int[][] mat = new int[p][p];
                    for (int i = 0; i < p; i++) //инициализация матрицы matr нулевыми значениями
                        for (int j = 0; j < p; j++)
                            mat[i][j] = 0;

                    for (int i = 0; i < p; i++)     //перемножение матриц
                    {
                        for (int j = 0; j < p; j++) {
                            int sum = 0;
                            for (int k = 0; k < p; k++) {
                                sum += matr[i][k] * matr[k][j];
                            }
                            mat[i][j] = sum;
                        }
                    }

                    for (int i = 0; i < p; i++)     //перенос временных значений из массива mat  в массив matr
                        for (int j = 0; j < p; j++) {
                            matr[i][j] = mat[i][j];
                            if (matr[i][j] != 0 && result[i][j] == 0 && i != j)
                                result[i][j] = t;
                        }

                }
                textArea.appendText("Расстояние между вершинами\n");

                for (int i = 0; i < p; i++)     //вывод матрицы смежности в степени lg на экран
                {
                    for (int j = 0; j < p; j++)
                        textArea.appendText("\t" + result[i][j]);
                    textArea.appendText("\n");
                }
                for (int j = 0; j < p; j++) { //Заполняем единичками, потому что они эти вершини мы удалили и следовательно на проверку они не должни попасть
                    result[v][j] = 1;
                    result[j][v] = 1;
                }
                for (int i = 0; i < p; i++)
                {
                    for (int j = 0; j < p; j++){
                        if (result[i][j] ==0 && i!=j ){
                            VerticeList.get(v).setArculation_point(true);
                            break;
                        }
                    }

                }


            }
            textArea.appendText("Точки сочления\n");
            for (Vertices vertices : VerticeList) {
                if(vertices.isArculation_point()==true){
                    vertices.setColor(Color.RED);
                    vertices.show(gc);
                    textArea.appendText("\t"+vertices.getNumber_vertice());
                }
            }

        }

    }

    @FXML
    void initialize() {
        this.gc = canvas.getGraphicsContext2D();
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("clicked");
                        click_x=e.getX();
                        click_y=e.getY();
                        if (add_vertices.isSelected()){
                            number_vertice++;
                            vertices=new Vertices(gc,click_x,click_y,number_vertice);
                            VerticeList.add(vertices);
                            vertices.show(gc);
                            p++;
                        }
                        if (add_arc.isSelected()) {
                            chooseshape(click_x,click_y);

                        }




                    }
                });

        assert textArea != null : "fx:id=\"textArea\" was not injected: check your FXML file 'sample.fxml'.";
        assert add_vertices != null : "fx:id=\"add_vertices\" was not injected: check your FXML file 'sample.fxml'.";
        assert tool_choose != null : "fx:id=\"tool_choose\" was not injected: check your FXML file 'sample.fxml'.";
        assert add_arc != null : "fx:id=\"add_arc\" was not injected: check your FXML file 'sample.fxml'.";
        assert vertice_from != null : "fx:id=\"vertice_from\" was not injected: check your FXML file 'sample.fxml'.";
        assert vertice_to != null : "fx:id=\"vertice_to\" was not injected: check your FXML file 'sample.fxml'.";
        assert find_button != null : "fx:id=\"find_button\" was not injected: check your FXML file 'sample.fxml'.";
        assert delete_button != null : "fx:id=\"delete_button\" was not injected: check your FXML file 'sample.fxml'.";
        assert vertice_from1 != null : "fx:id=\"vertice_from1\" was not injected: check your FXML file 'sample.fxml'.";
        assert vertice_to1 != null : "fx:id=\"vertice_to1\" was not injected: check your FXML file 'sample.fxml'.";
        assert Center_button != null : "fx:id=\"Center_button\" was not injected: check your FXML file 'sample.fxml'.";
        assert articulation_point != null : "fx:id=\"articulation_point\" was not injected: check your FXML file 'sample.fxml'.";
        assert bridge_button != null : "fx:id=\"bridge_button\" was not injected: check your FXML file 'sample.fxml'.";
        assert canvas != null : "fx:id=\"canvas\" was not injected: check your FXML file 'sample.fxml'.";

        textArea.setStyle("-fx-background-color: #dfe6e9;");
        textArea.setLayoutY(39);
        textArea.setPadding(new Insets(10, 10, 10, 10));
        textArea.setEditable(false);
        //textContainer.setContent(textArea);

    }
    public void chooseshape(double point_x, double point_y){

        for(int i = VerticeList.size()-1 ; i >= 0; i--){
            vertices=VerticeList.get(i);
            if(Math.sqrt(Math.pow(point_x-(vertices.getShow_x()+25/2),2.0)+Math.pow(point_y-(vertices.getShow_y()+25/2),2.0))<(25/2))
            {
                choose_detector++;
                System.out.println("Selected");
                System.out.println(choose_detector);
                if(choose_detector==1){
                    x1=vertices.getShow_x();
                    y1=vertices.getShow_y();
                    this.vertices_selected_from = vertices;
                    this.vertices_selected_from.show(gc);

                }
                if(choose_detector==2){
                    x2=vertices.getShow_x();
                    y2=vertices.getShow_y();
                    this.vertices_selected_to = vertices;
                    this.vertices_selected_to.show(gc);
                    arcs= new Arcs(x1,y1,x2,y2,vertices_selected_from.getNumber_vertice(),vertices_selected_to.getNumber_vertice());
                    arcs.show(gc);
                    ArcssList.add(arcs);
                    q++;
                    choose_detector=0;


                }



            }
        }
    }
}
