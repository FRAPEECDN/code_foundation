/*
* Program using modern regular expression implementation in C++
* it can run using STL string with regex, regex_search
* see https://regexr.com/ for reference as well as https://en.cppreference.com/w/cpp/regex
*/
#include <iostream>
#include <iterator>
#include <string>
#include <regex>
#include <map>

int main()
{
    std::string s = "Some people, when confronted with a problem, think "
        "\"I know, I'll use regular expressions.\" "
        "Now they have two problems.";

    std::regex self_regex("REGULAR EXPRESSIONS",
        std::regex_constants::ECMAScript | std::regex_constants::icase);
    if (std::regex_search(s, self_regex)) {
        std::cout << "Text contains the phrase 'regular expressions'\n";
    }

    std::regex word_regex("(\\w+)");
    auto words_begin =
        std::sregex_iterator(s.begin(), s.end(), word_regex);
    auto words_end = std::sregex_iterator();

    std::cout << "Found "
        << std::distance(words_begin, words_end)
        << " words\n";

    const int N = 6;
    std::cout << "Words longer than " << N << " characters:\n";
    for (std::sregex_iterator i = words_begin; i != words_end; ++i) {
        std::smatch match = *i;
        std::string match_str = match.str();
        if (match_str.size() > N) {
            std::cout << "  " << match_str << '\n';
        }
    }


    std::regex long_word_regex("(\\w{7,})");
    std::string new_s = std::regex_replace(s, long_word_regex, "[$&]");
    std::cout << new_s << '\n';

    std::map<std::string, std::string> variables{ {"name", "Bert"}, {"game", "c++ frustration"} };

    std::regex variableContext("(\\{\\{[a-z]*\\}\\})");
    std::string sentence("Hello {{name}}, welcome to the mustache game {{game}}");
    std::cout << sentence << std::endl;
    auto var_begin =
        std::sregex_iterator(sentence.begin(), sentence.end(), variableContext);
    auto var_end = std::sregex_iterator();

    std::cout << "Found "
        << std::distance(var_begin, var_end)
        << " variables" << std::endl;

    std::cout << "Show Variables" << std::endl;
    for (std::sregex_iterator i = var_begin; i != var_end; ++i) {
        std::smatch match = *i;
        std::string match_str = match.str();
        std::cout << "  " << match_str << std::endl;
    }
    std::cout << "End Variables" << std::endl;
    std::string replacedString;
    std::smatch variable;
    auto begin = sentence.cbegin();
    while (std::regex_search(begin, sentence.cend(), variable, variableContext))
    {
        std::string key = variable.str().substr(2, variable.str().size() - 4);
        replacedString += variable.prefix();
        auto replacement = variables.find(key)->second;
        replacedString += replacement;

        begin += variable.position() + variable.length();
    }
    replacedString += variable.suffix(); // copy the substring after the last match

    std::cout << replacedString << std::endl;
    std::cout << "Hello World!\n";
    std::string final;
    std::getline(std::cin, final);
}
