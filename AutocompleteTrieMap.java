import java.util.*;

public class AutocompleteTrieMap {

    static class TrieNode {
        char data;
        HashMap<Character, TrieNode> children = new HashMap<>();
        boolean isEnd = false;

        TrieNode(char c) {
            this.data = c;
        }
    }

    static class Trie {

        TrieNode root = new TrieNode(' ');

        void insert(String word) {
            TrieNode node = root;
            for (char ch : word.toCharArray()) {
                if (!node.children.containsKey(ch))
                    node.children.put(ch, new TrieNode(ch));
                node = node.children.get(ch);
            }
            node.isEnd = true;
        }

        ArrayList<String> autocomplete(String prefix) {
            ArrayList<String> res = new ArrayList<>();
            TrieNode node = root;
            for (char ch : prefix.toCharArray()) {
                if (node.children.containsKey(ch))
                    node = node.children.get(ch);
                else
                    return res;
            }
            helper(node, res, prefix.substring(0, prefix.length() - 1));
            return res;
        }

        void helper(TrieNode node, List<String> res, String prefix) {
            if (node.isEnd)
                res.add(prefix + node.data);
            for (Character ch : node.children.keySet())
                helper(node.children.get(ch), res, prefix + node.data);
        }
    }

    public static void main(String[] args) {

        Trie t = new Trie();
        int corpus;
        int numOfImperfectWords;
        Scanner scanner = new Scanner(System.in);

        corpus = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < corpus; i++) {
            t.insert(scanner.nextLine().toLowerCase());
        }

        numOfImperfectWords = Integer.parseInt(scanner.nextLine());
        ArrayList<String> imperfects = new ArrayList<>();

        for (int i = 0; i < numOfImperfectWords; i++) {
            imperfects.add(scanner.nextLine().toLowerCase());
        }

        //The output is in DFS order as you said we can do.
        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        for (int i = 0; i < imperfects.size(); i++) {
            temp.add(t.autocomplete(imperfects.get(i)));
            System.out.println(temp.get(i).size() + " suggestions for " + "'" + imperfects.get(i) + "':");
            String list = Arrays.toString(t.autocomplete(imperfects.get(i)).toArray()).toLowerCase().
                    replace("[", "").replace(",", " |").replace("]", "");
            System.out.println(list);
            System.out.println();
        }
    }
}