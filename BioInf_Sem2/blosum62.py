def BLOSUM62():
    return scoring_matrix('blosum62.txt')

def scoring_matrix(path):
    with open(path, 'r') as f:
        lines = f.read().strip().split('\n')
    scores = [lines[0].split()] + [l[1:].split() for l in lines[1:]]
    return scores