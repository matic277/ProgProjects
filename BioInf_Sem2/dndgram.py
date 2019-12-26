import numpy as np
from matplotlib import pyplot as plt
from scipy.cluster.hierarchy import dendrogram
from sklearn.cluster import AgglomerativeClustering


def plot_dendrogram(model, **kwargs):
    # Create linkage matrix and then plot the dendrogram

    # create the counts of samples under each node
    counts = np.zeros(model.children_.shape[0])
    n_samples = len(model.labels_)
    for i, merge in enumerate(model.children_):
        current_count = 0
        for child_idx in merge:
            if child_idx < n_samples:
                current_count += 1  # leaf node
            else:
                current_count += counts[child_idx - n_samples]
        counts[i] = current_count

    linkage_matrix = np.column_stack([model.children_, model.distances_,
                                      counts]).astype(float)

    # Plot the corresponding dendrogram
    dendrogram(linkage_matrix, **kwargs)

def plotAll(data):
    names = []
    names.append("Bos taurus")
    names.append("Mus musculus")
    names.append("Homo sapiens")
    names.append("Danio rerio")
    names.append("Canis lupus familiaris")
    names.append("Rattus norvegicus")
    names.append("Macaca mulatta")

    # setting distance_threshold=0 ensures we compute the full tree.
    model = AgglomerativeClustering(distance_threshold=0, n_clusters=None)

    # UPGMA algorithm (agglomerative clustering, average linkage, precomputed distances)
    # model = AgglomerativeClustering(distance_threshold=0, n_clusters=None, affinity='precomputed', linkage='average')

    for elt in data:
        model = model.fit(elt[1])
        plt.title('Hierarchical Clustering Dendrogram, Gene-set id: ' + elt[0])
        # plot the top three levels of the dendrogram
        plot_dendrogram(model, truncate_mode='level', p=3, labels=names)
        plt.xlabel("Number of points in node (or index of point if no parenthesis).")

        plt.show()
