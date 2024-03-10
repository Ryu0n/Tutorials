from modules.LSPNetwork import LSPNetwork


if __name__ == '__main__':
    emitter = LSPNetwork()
    flag = True

    while flag:
        emitter.ask_api()

        check = input('Loop (y/n) : ')
        if check != 'y':
            flag = False
