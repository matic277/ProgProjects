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


# iris = load_iris()
# X = iris.data
#
# # setting distance_threshold=0 ensures we compute the full tree.
# model = AgglomerativeClustering(distance_threshold=0, n_clusters=None)
#
# model = model.fit(X)
# plt.title('Hierarchical Clustering Dendrogram')
# # plot the top three levels of the dendrogram
# plot_dendrogram(model, truncate_mode='level', p=3)
# plt.xlabel("Number of points in node (or index of point if no parenthesis).")
# plt.show()

data = []
data.append([0, 0.09129798397851127, 0.05241011453318477, 0.39643317691833074, 0.04324814108011553, 0.08485326118638743, 0.055489147253193837])
data.append([0, 0, 0.08378450432580219, 0.3751783484831141, 0.0827172682865923, 0.029214988613980888, 0.08592354320890357])
data.append([0, 0, 0, 0.39643317691833074, 0.050364426255019815, 0.08378450432580219, 0.013492172399368788])
data.append([0, 0, 0, 0, 0.3947766328986638, 0.37356804190636517, 0.3914744811690961])
data.append([0, 0, 0, 0, 0, 0.08165154874670548, 0.050364426255019815])
data.append([0, 0, 0, 0, 0, 0, 0.08485326118638743])
data.append([0, 0, 0, 0, 0, 0, 0])

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

model = model.fit(data)
plt.title('Hierarchical Clustering Dendrogram')
# plot the top three levels of the dendrogram
plot_dendrogram(model, truncate_mode='level', p=3, labels=names)
plt.xlabel("Number of points in node (or index of point if no parenthesis).")

plt.show()