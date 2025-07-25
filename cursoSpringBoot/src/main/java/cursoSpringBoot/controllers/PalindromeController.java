package cursoSpringBoot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PalindromeController {

    @GetMapping("/validar-palindromo/{word}")
    public String palindrome(@PathVariable String word){
        if (isPalindrome(word)){
            return  "La palabra " + word + " Es un palindromo";
        }else {
            return  "La palabra " + word + " No es un palindromo";
        }
    }

    private boolean isPalindrome(String word){
        int longitud = word.length();
        for (int i = 0; i < longitud / 2; i++) {
            if (word.charAt(i) != word.charAt(longitud - i - 1)){
                return false;
            }
        }
        return true;
    }
}
