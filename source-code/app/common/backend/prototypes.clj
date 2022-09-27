
(ns app.common.backend.prototypes
    (:require [mid-fruits.json   :as json]
              [x.server-user.api :as user]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn added-document-prototype
  [request namespace document]
  (->> document (user/added-document-prototype request namespace)
                (json/trim-values)))

(defn updated-document-prototype
  [request namespace document]
  (->> document (user/updated-document-prototype request namespace)
                (json/trim-values)))

(defn duplicated-document-prototype
  [request namespace document]
  (->> document (user/duplicated-document-prototype request namespace)
                (json/trim-values)))
