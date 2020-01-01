import math

def getJukesCantorDistance(seq1, seq2):
    diff = 0
    ln = 0

    # differences in sequences, ignoring gaps
    for i in range(min(len(seq1), len(seq2))):
        if seq1[i] != "-" and seq2[i] != "-":
            ln += 1
            if seq1[i] != seq2[i]:
                diff += 1

    p = float(diff) / float(ln)
    return (-3.0 / 4.0 * math.log(1 - (4.0 / 3.0 * p)))