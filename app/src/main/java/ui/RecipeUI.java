package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

import data.RecipeFileHandler;

public class RecipeUI {
    private BufferedReader reader;
    private RecipeFileHandler fileHandler;

    public RecipeUI() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        fileHandler = new RecipeFileHandler();
    }

    public RecipeUI(BufferedReader reader, RecipeFileHandler fileHandler) {
        this.reader = reader;
        this.fileHandler = fileHandler;
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println();
                System.out.println("Main Menu:");
                System.out.println("1: Display Recipes");
                System.out.println("2: Add New Recipe");
                System.out.println("3: Search Recipe");
                System.out.println("4: Exit Application");
                System.out.print("Please choose an option: ");

                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        // 設問1: 一覧表示機能
                        displayRecipes();
                        break;
                    case "2":
                        // 設問2: 新規登録機能
                        addNewRecipe();
                        break;
                    case "3":
                        // 設問3: 検索機能
                        searchRecipe();
                        break;
                    case "4":
                        System.out.println("Exit the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error reading input from user: " + e.getMessage());
            }
        }
    }

    /**
     * 設問1: 一覧表示機能
     * RecipeFileHandlerから読み込んだレシピデータを整形してコンソールに表示します。
     */
    private void displayRecipes() {
        data.RecipeFileHandler fileHandler = new data.RecipeFileHandler();
        ArrayList<String> recipes = new ArrayList<>();
        recipes = fileHandler.readRecipes();

        // ArrayList のサイズが0以外の時実行する
        if (recipes.size() != 0) {
            System.out.println("Recipes:");

            // ArrayList 中身を分割格納してメニュー名表示
            for (String recipe : recipes) {
                System.out.println("-----------------------------------");
                String[] menuName = recipe.split(",", 2);
                System.out.println("Recipe Name: " + menuName[0]);
                System.out.println("Main Ingredients: " + menuName[1]);

            }
        }

    }

    /**
     * 設問2: 新規登録機能
     * ユーザーからレシピ名と主な材料を入力させ、RecipeFileHandlerを使用してrecipes.txtに新しいレシピを追加します。
     *
     * @throws java.io.IOException 入出力が受け付けられない
     */
    private void addNewRecipe() throws IOException {
        // ファイル書き込み用のインスタンスを生成
        data.RecipeFileHandler fileHandler = new data.RecipeFileHandler();

        // 標準入力準備
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // メニュー名の入力
        System.out.print("Enter recipe name: ");
        String recipeName = reader.readLine();
        // 材料の入力
        System.out.print("Enter main ingredients (comma separated):");
        String ingredients = reader.readLine();

        // ファイル書き込みメソッドを呼び出し、メニュー名、材料を渡す
        fileHandler.addRecipe(recipeName, ingredients);

        System.out.println("Recipe added successfully.");
    }

    /**
     * 設問3: 検索機能
     * ユーザーから検索クエリを入力させ、そのクエリに基づいてレシピを検索し、一致するレシピをコンソールに表示します。
     *
     * @throws java.io.IOException 入出力が受け付けられない
     */
    private void searchRecipe() throws IOException {
        // 標準入力準備
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter search query (e.g., 'name=Tomato&ingredient=Garlic'): ");
        String inputSearchWords = reader.readLine();
        String inputSearchWord[] = inputSearchWords.split("&");
        // inputMenuNames[1]に検索したいメニュー名を格納
        String inputMenuNames[] = inputSearchWord[0].split("=");
        // inputIngredient[1]に検索したい材料名を格納
        String inputIngredient[] = inputSearchWord[1].split("=");

        data.RecipeFileHandler fileHandler = new data.RecipeFileHandler();
        ArrayList<String> recipes = new ArrayList<>();
        recipes = fileHandler.readRecipes();

        System.out.println("Search Results:");

        // 識別のためcount変数を準備
        int count = 0;
        // ArrayList 中身を分割格納してメニュー名表示
        for (String recipe : recipes) {
            // メニュー名と材料を分割
            String[] menuName = recipe.split(",", 2);
            // 材料を分割
            String[] materials = menuName[1].split(",");

            if (menuName[0].contains(inputMenuNames[1])) {
                System.out.println(recipes.get(count));
            }

            count++;
        }

    }

}
