package com.brownian.morse;

public class Morse{
    public static enum Symbol { DOT, DASH, WORD_BOUNDARY_PAUSE, CHAR_BOUNDARY_PAUSE};

    public static Symbol[] toMorse(char letter){
        if(Character.isWhitespace(letter))
            return new Symbol[]{Symbol.WORD_BOUNDARY_PAUSE};

        letter = Character.toUpperCase(letter);
        switch(letter){
            case 'A':
                return new Symbol[]{Symbol.DOT, Symbol.DASH};
            case 'B':
                return new Symbol[]{Symbol.DASH, Symbol.DOT, Symbol.DOT, Symbol.DOT};
            case 'C':
                return new Symbol[]{Symbol.DASH, Symbol.DOT, Symbol.DASH, Symbol.DOT};
            case 'D':
                return new Symbol[]{Symbol.DASH, Symbol.DOT, Symbol.DOT};
            case 'E':
                return new Symbol[]{Symbol.DOT};
            case 'F':
                return new Symbol[]{Symbol.DOT, Symbol.DOT, Symbol.DASH, Symbol.DOT};
            case 'G':
                return new Symbol[]{Symbol.DASH, Symbol.DASH, Symbol.DOT};
            case 'H':
                return new Symbol[]{Symbol.DOT, Symbol.DOT, Symbol.DOT, Symbol.DOT};
            case 'I':
                return new Symbol[]{Symbol.DOT, Symbol.DOT};
            case 'J':
                return new Symbol[]{Symbol.DOT, Symbol.DASH, Symbol.DASH, Symbol.DASH};
            case 'K':
                return new Symbol[]{Symbol.DASH, Symbol.DOT, Symbol.DASH};
            case 'L':
                return new Symbol[]{Symbol.DOT, Symbol.DASH, Symbol.DOT, Symbol.DOT};
            case 'M':
                return new Symbol[]{Symbol.DASH, Symbol.DASH};
            case 'N':
                return new Symbol[]{Symbol.DASH, Symbol.DOT};
            case 'O':
                return new Symbol[]{Symbol.DASH, Symbol.DASH, Symbol.DASH};
            case 'P':
                return new Symbol[]{Symbol.DOT, Symbol.DASH, Symbol.DASH, Symbol.DOT};
            case 'Q':
                return new Symbol[]{Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DASH};
            case 'R':
                return new Symbol[]{Symbol.DOT, Symbol.DASH, Symbol.DOT};
            case 'S':
                return new Symbol[]{Symbol.DOT, Symbol.DOT, Symbol.DOT};
            case 'T':
                return new Symbol[]{Symbol.DASH};
            case 'U':
                return new Symbol[]{Symbol.DOT, Symbol.DOT, Symbol.DASH};
            case 'V':
                return new Symbol[]{Symbol.DOT, Symbol.DOT, Symbol.DOT, Symbol.DASH};
            case 'W':
                return new Symbol[]{Symbol.DOT, Symbol.DASH, Symbol.DASH};
            case 'X':
                return new Symbol[]{Symbol.DASH, Symbol.DOT, Symbol.DOT, Symbol.DASH};
            case 'Y':
                return new Symbol[]{Symbol.DASH, Symbol.DOT, Symbol.DASH, Symbol.DASH};
            case 'Z':
                return new Symbol[]{Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DOT};

            case '0':
                return new Symbol[]{Symbol.DASH, Symbol.DASH, Symbol.DASH, Symbol.DASH, Symbol.DASH};
            case '1':
                return new Symbol[]{Symbol.DOT, Symbol.DASH, Symbol.DASH, Symbol.DASH, Symbol.DASH};
            case '2':
                return new Symbol[]{Symbol.DOT, Symbol.DOT, Symbol.DASH, Symbol.DASH, Symbol.DASH};
            case '3':
                return new Symbol[]{Symbol.DOT, Symbol.DOT, Symbol.DOT, Symbol.DASH, Symbol.DASH};
            case '4':
                return new Symbol[]{Symbol.DOT, Symbol.DOT, Symbol.DOT, Symbol.DOT, Symbol.DASH};
            case '5':
                return new Symbol[]{Symbol.DOT, Symbol.DOT, Symbol.DOT, Symbol.DOT, Symbol.DOT};
            case '6':
                return new Symbol[]{Symbol.DASH, Symbol.DOT, Symbol.DOT, Symbol.DOT, Symbol.DOT};
            case '7':
                return new Symbol[]{Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DOT, Symbol.DOT};
            case '8':
                return new Symbol[]{Symbol.DASH, Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DOT};
            case '9':
                return new Symbol[]{Symbol.DASH, Symbol.DASH, Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DOT};

            case 'Ä':
                return new Symbol[]{Symbol.DOT, Symbol.DASH, Symbol.DOT, Symbol.DASH};
            case 'Á':
                return new Symbol[]{Symbol.DOT, Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DASH};
            case 'Å':
                return new Symbol[]{Symbol.DOT, Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DASH};
            case 'É':
                return new Symbol[]{Symbol.DOT, Symbol.DOT, Symbol.DASH, Symbol.DOT, Symbol.DOT};
            case 'Ñ':
                return new Symbol[]{Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DASH, Symbol.DASH};
            case 'Ö':
                return new Symbol[]{Symbol.DASH, Symbol.DASH, Symbol.DASH, Symbol.DOT};
            case 'Ü':
                return new Symbol[]{Symbol.DOT, Symbol.DOT, Symbol.DASH, Symbol.DASH};

            case '.':
                return new Symbol[]{Symbol.DOT, Symbol.DASH, Symbol.DOT, Symbol.DASH, Symbol.DOT, Symbol.DASH};
            case ',':
                return new Symbol[]{Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DOT, Symbol.DASH, Symbol.DASH};
            case ':':
                return new Symbol[]{Symbol.DASH, Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DOT, Symbol.DOT};
            case '?':
            default:
                return new Symbol[]{Symbol.DOT, Symbol.DOT, Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DOT};
            case '\'':
                return new Symbol[]{Symbol.DOT, Symbol.DASH, Symbol.DASH, Symbol.DASH, Symbol.DASH, Symbol.DOT};
            case '-':
                return new Symbol[]{Symbol.DASH, Symbol.DOT, Symbol.DOT, Symbol.DOT, Symbol.DASH};
            case '/':
                return new Symbol[]{Symbol.DASH, Symbol.DOT, Symbol.DOT, Symbol.DASH, Symbol.DOT};
            case '(':
                return new Symbol[]{Symbol.DASH, Symbol.DOT, Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DASH};
            case ')':
                return new Symbol[]{Symbol.DASH, Symbol.DOT, Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DASH};
            case '[':
                return new Symbol[]{Symbol.DASH, Symbol.DOT, Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DASH};
            case ']':
                return new Symbol[]{Symbol.DASH, Symbol.DOT, Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DASH};
            case '"':
                return new Symbol[]{Symbol.DOT, Symbol.DASH, Symbol.DOT, Symbol.DOT, Symbol.DASH, Symbol.DOT};
            case '@':
                return new Symbol[]{Symbol.DOT, Symbol.DASH, Symbol.DASH, Symbol.DOT, Symbol.DASH, Symbol.DOT};
            case '=':
                return new Symbol[]{Symbol.DASH, Symbol.DOT, Symbol.DOT, Symbol.DOT, Symbol.DASH};
        }
    }

    public static Symbol[][] toMorseWords(String text){
        Symbol[][] morseText = new Symbol[text.length()][];
        for(int i = 0 ; i < text.length() ; i++){
            morseText[i] = toMorse(text.charAt(i));
        }
        return morseText;
    }
}
