# Path of Exile: Item name extractor
This utility constantly checks the clipboard for copied item's stats and just keeps the name for quicker searches on poetrade.

## Usage
To compile the utility just run the following commands:
```
cd src
javac -encoding utf8 ClipboardNameExtractor.java
```

After that you can simply run the program with:
```
java ClipboardNameExtractor
```
And if you use `-m` or `--muted` the program won't make a noise when it processed an item's stats

If you want to keep the stats, you just have to copy it a second time and the program will ignore it.

## License
[Apache V2](LICENSE)
