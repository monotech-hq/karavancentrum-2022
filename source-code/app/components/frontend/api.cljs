
(ns app.components.frontend.api
  (:require
    [app.components.frontend.tabs.core :as tabs]
    [app.components.frontend.sortable.core :as sortable]
    [app.components.frontend.text-editor.core :as text-editor]))

(def tabs tabs/view)

(def sortable sortable/view)

(def text-editor text-editor/view)
