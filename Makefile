NAME = GameOfLife.jar
SRC = Cell.java \
      GameOfLife.java  \
      Generation.java  \
      Main.java


SRC_DIR = src

SRCS = $(addprefix $(SRC_DIR)/, $(SRC))

all: $(NAME)
	
$(NAME): compile
	jar -cmf GameOfLife.mf $(NAME) -C build/ .

compile:
	javac -d build $(SRCS)

run:
	java -jar $(NAME)

clean:
	rm -fr build

fclean: clean
	rm $(NAME)
