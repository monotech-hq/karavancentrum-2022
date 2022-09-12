
(ns app.vehicles.frontend.editor.sortable-grid.utils)

(defn to-clj-map [hash-map]
  (js->clj hash-map :keywordize-keys true))
