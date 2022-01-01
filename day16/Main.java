import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.nio.Buffer;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.lang.Math;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.List;

public class Main {
    public static String hexToBin(String hex) {
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }

    static class PacketResult {
        int version_sum;
        int packet_lenght;

        public PacketResult(int version, int packet) {
            version_sum = version;
            packet_lenght = packet;
        }
    }

    static class PacketResult_2 {
        long result;
        long packet_lenght;

        public PacketResult_2(long long_val, int packet_len) {
            result = long_val;
            packet_lenght = packet_len;
        }
    }

    static PacketResult parse_message_packet_1(String packet) {
        int version_sum = 0;
        int version = Integer.parseInt(packet, 0, 3, 2);
        version_sum += version;
        int type_ID = Integer.parseInt(packet, 3, 6, 2);

        if (type_ID == 4) {
            int packet_counter = 0;
            while (true) {
                if (packet.charAt(6 + packet_counter * 5) == '0') {
                    packet_counter += 1;
                    break;
                } else {
                    packet_counter += 1;
                }
            }
            PacketResult result = new PacketResult(version_sum, 6 + packet_counter * 5);
            return result;
        } else {
            int lenght_ID = Integer.parseInt(packet, 6, 7, 2);
            if (lenght_ID == 0) {
                int total_sub_len = Integer.parseInt(packet, 7, 22, 2);
                int parsed_sub_len = 0;
                while (total_sub_len > parsed_sub_len) {
                    PacketResult result = parse_message_packet_1(packet.substring(22 + parsed_sub_len));
                    version_sum += result.version_sum;
                    parsed_sub_len += result.packet_lenght;
                }
                return new PacketResult(version_sum, 22 + parsed_sub_len);
            } else if (lenght_ID == 1) {
                int total_subpac = Integer.parseInt(packet, 7, 18, 2);
                int parsed_sub_len = 0;
                for (int i = 0; i < total_subpac; i++) {
                    PacketResult result = parse_message_packet_1(packet.substring(18 + parsed_sub_len));
                    version_sum += result.version_sum;
                    parsed_sub_len += result.packet_lenght;
                }
                return new PacketResult(version_sum, 18 + parsed_sub_len);
            } else {
                System.out.println("Error at parsing");
            }
        }
        return new PacketResult(version_sum, 7);
    }

    static ArrayList<String> readFile(String file_name) {
        ArrayList<String> file_lines = new ArrayList<>();
        try {
            File myObj = new File(file_name);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                file_lines.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return file_lines;
    }

    static void question_1() {
        ArrayList<String> filelines = readFile("data.txt");
        String fileline = hexToBin(filelines.get(0));
        PacketResult result = parse_message_packet_1(fileline);
        System.out.println(result.version_sum);
    }

    static Long operation(int type_id, ArrayList<Long> values) {
        long operation_result = 0;
        if (type_id == 0) {
            operation_result = 0;
            for (int i = 0; i < values.size(); i++) {
                operation_result += values.get(i);
            }
        } else if (type_id == 1) {
            operation_result = 1;
            for (int i = 0; i < values.size(); i++) {
                operation_result *= values.get(i);
            }
        } else if (type_id == 2) {
            operation_result = Integer.MAX_VALUE;
            for (int i = 0; i < values.size(); i++) {
                if (operation_result > values.get(i)) {
                    operation_result = values.get(i);
                }
            }
        } else if (type_id == 3) {
            operation_result = Integer.MIN_VALUE;
            for (int i = 0; i < values.size(); i++) {
                if (operation_result < values.get(i)) {
                    operation_result = values.get(i);
                }
            }
        } else if (type_id == 5) {
            operation_result = values.get(0) > values.get(1) ? 1 : 0;
        } else if (type_id == 6) {
            operation_result = values.get(0) < values.get(1) ? 1 : 0;
        } else if (type_id == 7) {
            operation_result = values.get(0) == values.get(1) ? 1 : 0;
        }
        return operation_result;
    }

    static PacketResult_2 parse_message_packet_2(String packet) {
        int version = Integer.parseInt(packet, 0, 3, 2);
        int type_ID = Integer.parseInt(packet, 3, 6, 2);
        ArrayList<Long> results;
        if (type_ID == 4) {
            String number = "";
            Long result_long = Long.MIN_VALUE;
            int packet_counter = 0;
            while (true) {
                if (packet.charAt(6 + packet_counter * 5) == '0') {
                    number = number + packet.substring(7 + packet_counter * 5, 6 + 5 + packet_counter * 5);
                    packet_counter += 1;
                    break;
                } else {
                    number = number + packet.substring(7 + packet_counter * 5, 6 + 5 + packet_counter * 5);
                    packet_counter += 1;
                }
            }
            result_long = Long.parseLong(number, 2);
            return new PacketResult_2(result_long, 6 + packet_counter * 5);
        } else {
            int lenght_ID = Integer.parseInt(packet, 6, 7, 2);
            results = new ArrayList<>();
            if (lenght_ID == 0) {
                int total_sub_len = Integer.parseInt(packet, 7, 22, 2);
                int parsed_sub_len = 0;
                while (total_sub_len > parsed_sub_len) {
                    PacketResult_2 result = parse_message_packet_2(packet.substring(22 + parsed_sub_len));
                    results.add(result.result);
                    parsed_sub_len += result.packet_lenght;
                }

                return new PacketResult_2(operation(type_ID, results), 22 + parsed_sub_len);
            } else if (lenght_ID == 1) {
                int total_subpac = Integer.parseInt(packet, 7, 18, 2);
                int parsed_sub_len = 0;
                for (int i = 0; i < total_subpac; i++) {
                    PacketResult_2 result = parse_message_packet_2(packet.substring(18 + parsed_sub_len));
                    results.add(result.result);
                    parsed_sub_len += result.packet_lenght;
                }
                return new PacketResult_2(operation(type_ID, results), 18 + parsed_sub_len);
            } else {
                System.out.println("Error at parsing");
            }
        }
        return new PacketResult_2(Long.MIN_VALUE, 7);
    }

    static void question_2() {
        ArrayList<String> filelines = readFile("data.txt");
        String fileline = hexToBin(filelines.get(0));
        PacketResult_2 result = parse_message_packet_2(fileline);
        System.out.println(result.result);
    }

    public static void main(String[] args) {
        // question_1();
        question_2();
    }
}
