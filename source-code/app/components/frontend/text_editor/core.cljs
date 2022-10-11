

(ns app.components.frontend.text-editor.core
  (:require
    ; [ckeditor4-react :refer [CKEditor]]
    [re-frame.core :as r]
    ["@ckeditor/ckeditor5-react" :refer [CKEditor]]
    ["@ckeditor/ckeditor5-build-classic" :as a]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn text-editor []
  (let [value @(r/subscribe [:db/get-item [::value] "<p>Hello from CKEditor 5!</p>"])]

    [:div
     [:h1 "text-editor"]

     [:> CKEditor
      {:editor a
       :config {:toolbar ["asd" "|" "heading" "|" "bold" "italic" "link" "bulletedList" "numberedList" "blockQuote"]
                :heading {:options [{:model "paragraph" :title "Paragraph" :class "ck-heading_paragraph"}
                                    {:model "heading1" :view "h1" :title "custom 1" :class "ck-heading_heading1"}
                                    {:model "heading2" :view "h2" :title "Heading 2" :class "ck-heading_heading2"}]}}
       :data  value
       :onReady   (fn [editor] (println "im ready" editor))
       :on-change (fn [event editor]
                    (let [data (.getData editor)]
                      (r/dispatch [:db/set-item! [::value] data])))}]
     [:p (str value)]]))
;; ---- Components ----
;; -----------------------------------------------------------------------------

(defn view []
  [text-editor])
