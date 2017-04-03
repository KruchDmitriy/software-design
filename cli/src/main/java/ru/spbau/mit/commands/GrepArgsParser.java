package ru.spbau.mit.commands;

import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.Unparsed;

import java.util.List;

/**
 * Interface for jewelcli arguments parser.
 * Every method define an argument option. Short name is "-v"
 * option, long name -- "--version", description is a message
 * that prints with this option in help. Unparsed is an option
 * without key (-v or --version). You can call help via --help
 * or -h option.
 */
interface GrepArgsParser {
    @Option(shortName = "i",
            longName = "ignore-case",
            description = "Ignore case distinctions in both the PATTERN\n"
                        + "\t\tand input files (from grep manual).")
    boolean isIgnoreCase();

    @Option(shortName = "w",
            longName = "word-regexp",
            description = "Select only those lines containing matches\n"
                    + "\t\tthat form whole words. The test is that the\n"
                    + "\t\tmatching substring must either be at the\n"
                    + "\t\tbeginning of the line, or preceded by a non-word\n"
                    + "\t\tconstituent character. Word-constituent characters\n"
                    + "\t\tare letters, digits, and the underscore\n"
                    + "\t\t(from grep manual).")
    boolean isWordRegexp();

    @Option(shortName = "A",
            longName = "after-context",
            defaultValue = "0",
            description = "Print NUM lines of trailing context\n"
                    + "\t\tafter matching")
    int getNumLinesAfter();

    @Unparsed(minimum = 1, maximum = 2)
    List<String> getUnparsed();

    @Option(helpRequest = true, description = "display help", shortName = "h")
    boolean getHelp();
}
