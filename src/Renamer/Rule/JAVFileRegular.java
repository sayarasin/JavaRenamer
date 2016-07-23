package Renamer.Rule;

/**
 * Created by sayarasin on 07/23/2016.
 */
public class JAVFileRegular extends GeneralRegular {

    public String rename(String oldName) {

        String name [] = oldName.split("\\.");
        if(name.length != 2)
            return null;

        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < name[0].length() ; ++i) {
            char current = name[0].charAt(i);
            if((current >= 'A' && current <= 'Z')
                || (current >= 'a' && current <= 'z') || (current >= '0' && current <= '9')) {
                sb.append(current);
            }
        }

        String clean = sb.toString();
        sb.setLength(0);

        if(clean.matches("[a-zA-Z]{2,4}[0-9]{3,4}[a-zA-Z*]")) {

            for(int i = 0 ; i < clean.length() ; ++i) {
                sb.append(Character.toUpperCase(clean.charAt(i)));
                if(i < clean.length() - 1
                        && Character.isLetter(clean.charAt(i))
                        && Character.isDigit(clean.charAt(i + 1)))
                    sb.append("-");
            }

            sb.append(".");
            sb.append(name[1]);

            return sb.toString();
        } else
            return null;
    }
}
