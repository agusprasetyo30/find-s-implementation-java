/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author AgusPrasetyo
 */
public class Main {

    private static String[][] trainingAttributes, testingAttributes;
    private static String[] hypotesis;

    // File CSV
    private static final String TRAINING_DATA_FILES = "E:\\Data\\Kuliah (PENS)\\2. Semester 7\\5. (Praktikum) Mesin Pembelajaran (Machine Learning)\\2. Find-S\\find-s-java\\training.csv";
    private static final String TESTING_DATA_FILES = "E:\\Data\\Kuliah (PENS)\\2. Semester 7\\5. (Praktikum) Mesin Pembelajaran (Machine Learning)\\2. Find-S\\find-s-java\\testing.csv";

    public static void main(String[] args) {
        trainingAttributes = setData(TRAINING_DATA_FILES);
        testingAttributes = setData(TESTING_DATA_FILES);

        getHypotesis("Yes");
//        getHypotesis("No");

        testHypotesis();
    }

    private static String[][] setData(String filePath) {
        ArrayList<String[]> resultList = new ArrayList<>(); // List untuk menampung sementara isi file
        String[][] attributes = null; // Array of string yang akan dikembalikan

        try {
            File file = new File(filePath); // Membuat object file dari file yang diinginkan
            BufferedReader br = new BufferedReader(new FileReader(file)); // Membuat object untuk membaca file
            String str = "";

            /*
             * Looping untuk membaca keseluruhan isi file
             */
            while (str != null) {
                str = br.readLine(); // Mengambil setiap baris file

                if (str != null) // Jika baris ada
                    resultList.add(str.split(" ")); // Memisahkan setiap kata menurut spasi (" ")
            }
        } catch (FileNotFoundException e) { // Jika file tidak ditemukan
            System.out.println("File tidak ditemukan");
        } catch (IOException e) { // Jika terjadi kesalahan saat membaca file
            System.out.println("Kesalahan saat membaca file");
        } finally {
            if (resultList.size() > 0) { // Jika file tidak kosong
                int rowSize = resultList.size();
                int columnSize = resultList.get(0).length;

                attributes = new String[rowSize][columnSize]; // Membuat array of string berukuran data
                attributes = resultList.toArray(attributes); // Menyimpan data dari list ke array of string
            }
        }

        return attributes; // Mengembalikan data
    }

    private static void getHypotesis(String target) {
        int attributesLength = trainingAttributes.length;

        System.out.println("Search hypotesis with target \"" + target + "\"...");
        System.out.println("");

        if (attributesLength > 0) { // Pengecekan apakah attribute ada isinya
            int instanceLength = trainingAttributes[0].length;

            if (instanceLength > 1) { // Pengecekan apakah attribute minimal memiliki 2 instance
                instanceLength--; // Jumlah instance dikurangi satu agar tidak membaca target

                hypotesis = new String[instanceLength]; // Membuat array hipotesis sebesar jumlah instance
                int attributeFirstIndex = 0;

                /*
                 * Pengambilan dataset pertama yang memiliki target yang diinginkan
                 */
                for (int i = 0; i < attributesLength; i++) {
                    if (trainingAttributes[i][instanceLength].equals(target)) {
                        attributeFirstIndex = i;
                        break;
                    }
                }

                /*
                 * Menyimpan dataset pertama di array hipotesis
                 */
                System.arraycopy(trainingAttributes[attributeFirstIndex], 0, hypotesis, 0, instanceLength);

                /*
                 * Proses membuat instance yang berbeda disetiap dataset mejadi '?'
                 */
                for (int i = 1; i < attributesLength; i++) {
                    if (trainingAttributes[i][instanceLength].equals(target)) {
                        for (int j = 0; j < instanceLength; j++) {
                            if (!hypotesis[j].equals(trainingAttributes[i][j]))
                                hypotesis[j] = "?";
                        }
                    }
                }

                /*
                 * Menampilkan hipotesis yang telah didapatkan
                 */
                for (String h : hypotesis)
                    System.out.println(h);

                System.out.println("");
            } else
                System.out.println("Instance must over than 1, and the last column mus be target...");
        } else
            System.out.println("Attribute is empty...");
    }

    private static void testHypotesis() {
        boolean isSame; // Untuk mengecek apakah hipotesa sama dengan data testing

        /*
         * Mengecek keseluruhan data testing
         */
        for (String[] testingArray : testingAttributes) {
            System.out.print("Testing data : ");

            isSame = true; // Mengasumsikan hipotesa mirip dengan data testing

            /*
             * Mengecek setiap attribute pada data testing
             */
            for (int i = 0; i < testingArray.length; i++) {
                String attribute = testingArray[i]; // Mengambil attribute

                /*
                 * Mengecek apakah data hipotesa mirip dengan data testing
                 */
                if (!hypotesis[i].equals("?") && !attribute.equals(hypotesis[i]))
                    isSame = false;

                attribute += (i == (testingArray.length - 1) ? " " : ", ");
                System.out.print(attribute);
            }

            /*
             * Mengecek kesimpulan dan menampilkan
             */
            if (isSame)
                System.out.println("= Yes");
            else
                System.out.println("= No");
        }
    }
}
