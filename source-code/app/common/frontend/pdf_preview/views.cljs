
(ns app.common.frontend.pdf-preview.views
    (:require [mid-fruits.random :as random]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- pdf-preview
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  ;  {:src (string)}
  [_ {:keys [src]}]
  [:iframe {:src src :title "Preview" :style {:width "100%" :height "600px" :border-radius "var( --border-radius-m )"}}])

(defn element
  ; @param (keyword)(opt) preview-id
  ; @param (map) preview-props
  ;  {:src (string)}
  ;
  ; @usage
  ;  [common/pdf-preview {...}]
  ;
  ; @usage
  ;  [common/pdf-preview :my-preview {...}]
  ([preview-props]
   [element (random/generate-keyword) preview-props])

  ([preview-id preview-props]
   (let [] ;preview-props (pdf-preview.prototypes/preview-props-prototype preview-props)
        [pdf-preview preview-id preview-props])))
