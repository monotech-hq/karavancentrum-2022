
(ns app.pages.frontend.lister.views
    (:require [app.common.frontend.api     :as common]
              [app.components.frontend.api :as components]
              [elements.api                :as elements]
              [layouts.surface-a.api       :as surface-a]
              [re-frame.api                :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  ; @param (keyword) surface-id
  [_]
  [:<>])

(defn view
  ; @param (keyword) surface-id
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
