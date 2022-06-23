class FileManagement:

    # read contents of file and return a list of results
    def readFromFile(self, path):
        arr = []
        line = ""
        try:
            with open(path, 'r') as f:
                line = f.read()
            f.close()
        except:
            print("Error in locating file")
        arr = line.split('\n')
        return arr

    def clearFileContent(self, path):
        with open(path, 'w') as f:
            pass
        f.close()
        return

    # writes content by line
    def writeToFile(self, method, path, content):
        with open(path, method) as f:
            f.write(content)
        f.close()
