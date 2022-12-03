import java.io.FileWriter;

public class GenerateMap {
    final int numRows = 16;
    final int numColumns = 6;

    boolean isDone = false;

    int[] ans = new int[1000000];
    int numAns = 0;

    boolean[][] trace = new boolean[numRows][numColumns];

    public void Solution(){
        for (int i = 0; i < numColumns; i++) {
            Generate(0, i);
        }
    }

    public void printMap(){
        try {
            FileWriter fw = new FileWriter("./maps.txt");

            // Print
            int numMaps = numAns/(16 * 6) + 1;
            fw.write(numMaps + "");
            fw.write(System.lineSeparator());

            for (int i = 0; i < numAns; i++){
                fw.write(ans[i] + " ");
                if ((i + 1) % (16 * 6) == 0){
                    fw.write(System.lineSeparator());
                }
            }

            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void save(){
        for (int i = numRows - 1; i >= 0; i--){
            for (int j = 0; j < numColumns; j++){
                ans[numAns] = trace[i][j] == true ? 1 : 0;
                numAns++;
            }
        }

        if (numAns / (16 * 6) + 1 == 5000){
            isDone = true;
            printMap();
        }
    }

    boolean getTrace(int x, int y){
        if (x < 0 || y < 0 || y >= numColumns || x >= numRows) {
            return false;
        }
        return trace[x][y];
    }

    public boolean checkIs2x2(int x, int y){
        if (getTrace(x - 1, y) && getTrace(x - 1, y - 1) && getTrace(x, y - 1)){
            return true;
        }

        if (getTrace(x - 1, y) && getTrace(x - 1, y + 1) && getTrace(x, y + 1)){
            return true;
        }

        return false;
    }

    public boolean checkIsEnoughGreenBlock(){
        int count = 0;
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numColumns; j++){
                count += trace[i][j] == true ? 1 : 0;
            }
        }

        if (count < 23 || count > 25){
            return false;
        }
        return true;
    }

    public void Generate(int x, int y){
        if (isDone) return;

        if (y < 0 || y >= numColumns) return;

        // Check trường hợp hình vuông 2x2
        if (checkIs2x2(x, y)){
            return;
        }

        // Check trường hợp nếu đã đi qua
        if (getTrace(x, y)){
            return;
        }

        // Đánh dấu đã đi qua
        trace[x][y] = true;

        // Start row
        if (x == 0){
            // Đi lên
            Generate(x + 1, y);
        } else
        // Goal row
        if (x == numRows - 1) {
            // Nếu đủ số lượng green road thì lưu
            if (checkIsEnoughGreenBlock()){
                save();
            }
        } else {
            // Đi lên và sang 2 bên
            Generate(x + 1, y);
            Generate(x, y - 1);
            Generate(x, y + 1);
        }

        // Đánh dấu chưa đi qua
        trace[x][y] = false;
    }
}
