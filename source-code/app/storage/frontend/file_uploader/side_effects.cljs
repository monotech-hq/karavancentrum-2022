
(ns app.storage.frontend.file-uploader.side-effects
    (:require [app.storage.frontend.file-uploader.views :as file-uploader.views]
              [dom.api                                  :as dom]
              [x.app-core.api                           :as a]
              [x.app-tools.api                          :as tools]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn any-file-selected?
  []
  (let [file-selector (dom/get-element-by-id "storage--file-selector")]
       (dom/file-selector->any-file-selected? file-selector)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn open-file-selector!
  [uploader-id uploader-props]
  (tools/append-temporary-component! [file-uploader.views/file-selector uploader-id uploader-props]
                                    #(-> "storage--file-selector" dom/get-element-by-id .click)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-fx :storage.file-uploader/open-file-selector! open-file-selector!)
