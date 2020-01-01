import sys


def matchScore(m, a, b):
    if a == b:
        return 1
    if a == "-" or b == "-":
        return -2
    return -1


def globalAlignAffine(s1, s2):
    gap = -11
    egap = -1
    traceM = [[0 for j in range(len(s2) + 1)] for i in range(len(s1) + 1)]
    traceX = [[0 for j in range(len(s2) + 1)] for i in range(len(s1) + 1)]
    traceY = [[0 for j in range(len(s2) + 1)] for i in range(len(s1) + 1)]

    M = [[0 for j in range(len(s2) + 1)] for i in range(len(s1) + 1)]  # mismatch
    X = [[0 for j in range(len(s2) + 1)] for i in range(len(s1) + 1)]  # gap x
    Y = [[0 for j in range(len(s2) + 1)] for i in range(len(s1) + 1)]  # gap y

    for i in range(1, len(s1) + 1):
        M[i][0] = gap + egap * (i - 1)
        X[i][0] = -sys.maxsize - 1
        Y[i][0] = -sys.maxsize - 1
    for i in range(1, len(s2) + 1):
        M[0][i] = gap + egap * (i - 1)
        X[0][i] = -sys.maxsize - 1
        Y[0][i] = -sys.maxsize - 1

    for i in range(1, len(s1) + 1):
        for j in range(1, len(s2) + 1):
            costX = [M[i - 1][j] + gap,
                     X[i - 1][j] + egap]
            X[i][j] = max(costX)
            traceX[i][j] = costX.index(X[i][j])

            costY = [M[i][j - 1] + gap,
                     Y[i][j - 1] + egap]
            Y[i][j] = max(costY)
            traceY[i][j] = costY.index(Y[i][j])

            costM = [M[i - 1][j - 1] + matchScore(s1[i - 1], s2[j - 1]),
                     X[i][j],
                     Y[i][j]]
            M[i][j] = max(costM)
            traceM[i][j] = costM.index(M[i][j])

    scores = [X[i][j], Y[i][j], M[i][j]]
    sAlign, tAlign = s1, s2
    maxScore = M[-1][-1]
    maxScore = max(scores)
    traceback = scores.index(maxScore)
    i, j = len(s1), len(s2)

    while i > 0 and j > 0:
        if traceback == 0:
            if traceX[i][j] == 0:
                traceback = 2
            i -= 1
            tAlign = tAlign[:j] + "-" + tAlign[j:]
        elif traceback == 1:
            if traceY[i][j] == 0:
                traceback = 2
            j -= 1
            sAlign = sAlign[:i] + "-" + sAlign[i:]
        elif traceback == 2:
            if traceM[i][j] == 1:
                traceback = 0
            elif traceM[i][j] == 2:
                traceback = 1
            else:
                i -= 1
                j -= 1

    for remaining in range(j):
        sAlign = sAlign[:0] + '-' + sAlign[0:]

    for remaining in range(i):
        tAlign = tAlign[:0] + '-' + tAlign[0:]

    return str(maxScore), sAlign, tAlign


def alignSequences(s1, s2):
    return globalAlignAffine(s1, s2)